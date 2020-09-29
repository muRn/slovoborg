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

        Opinion existingOpinion = existingOpinionOpt.get();
        long existingOpinionId = existingOpinion.getId();
        if (opinion.getOpinion() == 1) {
            if (existingOpinion.getOpinion() == 1) {
                opinionRepo.updateOpinion(0, existingOpinionId);
                definitionRepo.unlike(definitionId);
            } else if (existingOpinion.getOpinion() == 0) {
                opinionRepo.updateOpinion(1, existingOpinionId);
                definitionRepo.like(definitionId);
            } else if (existingOpinion.getOpinion() == -1) {
                opinionRepo.updateOpinion(1, existingOpinionId);
                definitionRepo.undislike(definitionId);
                definitionRepo.like(definitionId);
            } else {
                throw new RuntimeException("Opinion " + existingOpinionId + " somehow has opinion " + existingOpinion.getOpinion() + " not in [-1..1]");
            }
        } else if (opinion.getOpinion() == -1) {
            if (existingOpinion.getOpinion() == 1) {
                opinionRepo.updateOpinion(-1, existingOpinionId);
                definitionRepo.unlike(definitionId);
                definitionRepo.dislike(definitionId);
            } else if (existingOpinion.getOpinion() == 0) {
                opinionRepo.updateOpinion(-1, existingOpinionId);
                definitionRepo.dislike(definitionId);
            } else if (existingOpinion.getOpinion() == -1) {
                opinionRepo.updateOpinion(0, existingOpinionId);
                definitionRepo.undislike(definitionId);
            } else {
                throw new RuntimeException("Opinion " + existingOpinionId + " somehow has opinion " + existingOpinion.getOpinion() + " not in [-1..1]");
            }
        } else {
            return false;
        }

        return true;
    }
}
