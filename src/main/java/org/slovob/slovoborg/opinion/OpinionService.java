package org.slovob.slovoborg.opinion;

import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpinionService {
    private OpinionRepository opinionRepo;
    private DefinitionRepository definitionRepo;

    public OpinionService(OpinionRepository opinionRepo, DefinitionRepository definitionRepo) {
        this.opinionRepo = opinionRepo;
        this.definitionRepo = definitionRepo;
    }

    public boolean processOpinion(Opinion opinion) {
        Optional<Definition> definition = definitionRepo.findById(opinion.getDefinitionId());
        if (!definition.isPresent()) {
            return false;
        }

        long definitionId = definition.get().getId();
        Optional<Opinion> existingOpinionOpt = opinionRepo.findByDefinitionIdAndIpAddress(opinion.getDefinitionId(), opinion.getIpAddress());
        if (!existingOpinionOpt.isPresent()) {
            opinionRepo.save(opinion);
            if (opinion.getOpinion() == 1) {
                definitionRepo.like(definitionId);
            } else if (opinion.getOpinion() == -1) {
                definitionRepo.dislike(definitionId);
            }
            return true;
        }

        int existingOpinion = existingOpinionOpt.get().getOpinion();
        long existingOpinionId = existingOpinionOpt.get().getId();
        if (existingOpinion < -1 || existingOpinion > 1) {
            throw new RuntimeException("Opinion " + existingOpinionId + " somehow has opinion " + existingOpinion + " not in [-1..1]");
        }

        if (opinion.getOpinion() == 1) { // user pressed like
            if (existingOpinion == 1) {
                opinionRepo.updateOpinion(0, existingOpinionId);
                definitionRepo.unlike(definitionId);
            } else {
                opinionRepo.updateOpinion(1, existingOpinionId);
                definitionRepo.like(definitionId);
                if (existingOpinion == -1) {
                    definitionRepo.undislike(definitionId);
                }
            }
        } else if (opinion.getOpinion() == -1) { // user pressed dislike
            if (existingOpinion == -1) {
                opinionRepo.updateOpinion(0, existingOpinionId);
                definitionRepo.undislike(definitionId);
            } else {
                opinionRepo.updateOpinion(-1, existingOpinionId);
                definitionRepo.dislike(definitionId);
                if (existingOpinion == 1) {
                    definitionRepo.unlike(definitionId);
                }
            }
        } else { // user knows something about browser console/postman
            return false;
        }

        return true;
    }
}
