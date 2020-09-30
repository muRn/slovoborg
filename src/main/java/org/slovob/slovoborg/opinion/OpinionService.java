package org.slovob.slovoborg.opinion;

import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.slovob.slovoborg.exception.FishyFrontendQuery;
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

    public void processOpinion(OpinionTransfer opinion, String ipAddress) {
        long definitionId = opinion.getDefinitionId();
        Optional<Definition> definition = definitionRepo.findById(definitionId);
        if (!definition.isPresent()) {
            throw new FishyFrontendQuery("There is no definition with id " + definitionId);
        }

        Optional<Opinion> existingOpinionOpt = opinionRepo.findByDefinitionIdAndIpAddress(definitionId, ipAddress);
        if (!existingOpinionOpt.isPresent()) {
            opinionRepo.save(new Opinion(opinion, ipAddress));
            if (opinion.getOpinion() == 1) {
                definitionRepo.like(definitionId);
            } else if (opinion.getOpinion() == -1) {
                definitionRepo.dislike(definitionId);
            }
            return;
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
            throw new FishyFrontendQuery("There is no button with opinion " + opinion.getOpinion());
        }
    }
}
