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

    public void processOpinion(OpinionTransfer opinionDto, String ipAddress) {
        long definitionId = opinionDto.getDefinitionId();
        Optional<Definition> definitionOpt = definitionRepo.findById(definitionId);
        if (!definitionOpt.isPresent()) {
            throw new FishyFrontendQuery("There is no definition with id " + definitionId);
        }

        Definition definition = definitionOpt.get();
        Optional<Opinion> existingOpinionOpt = opinionRepo.findByDefinitionIdAndIpAddress(definitionId, ipAddress);
        Opinion opinion;
        if (!existingOpinionOpt.isPresent()) {
            opinion = new Opinion(opinionDto.getOpinion(), opinionDto.getDefinitionId(), ipAddress);
            if (opinionDto.getOpinion() == 1) {
                definition.like();
            } else if (opinionDto.getOpinion() == -1) {
                definition.dislike();
            }
        } else {
            opinion = existingOpinionOpt.get();
            if (opinionDto.getOpinion() == opinion.getOpinion()) { // user revoked previous opinion
                opinion.setOpinion(0);
                if (opinionDto.getOpinion() == 1) { // revoking like
                    definition.unlike();
                } else { // revoking dislike
                    definition.undislike();
                }
            } else { // user provided opposite opinion
                opinion.setOpinion(opinionDto.getOpinion());
                if (opinionDto.getOpinion() == 1) { // dislike to like
                    definition.like();
                    definition.undislike();
                } else { // like to dislike
                    definition.dislike();
                    definition.unlike();
                }
            }
        }

        definitionRepo.save(definition);
        if (opinion.getOpinion() != 0) {
            opinionRepo.save(opinion);
        } else {
            opinionRepo.deleteById(opinion.getId());
        }
    }
}
