package org.slovob.slovoborg;

import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.slovob.slovoborg.definition.DefinitionTransfer;
import org.slovob.slovoborg.opinion.Opinion;
import org.slovob.slovoborg.opinion.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultService {
    private DefinitionRepository definitionRepo;
    private OpinionRepository opinionRepo;

    @Autowired
    public DefaultService(DefinitionRepository definitionRepo, OpinionRepository opinionRepo) {
        this.definitionRepo = definitionRepo;
        this.opinionRepo = opinionRepo;
    }

    public List<DefinitionTransfer> getDefinitions(String clientIp) {
        List<Definition> definitions = definitionRepo.findByApproved(true);
        List<DefinitionTransfer> result = new ArrayList<>();
        for (Definition d : definitions) {
            DefinitionTransfer dt = new DefinitionTransfer(d);
            Optional<Opinion> opinionOpt = opinionRepo.findByDefinitionIdAndIpAddress(d.getId(), clientIp);
            if (opinionOpt.isPresent()) {
                Opinion opinion = opinionOpt.get();
                if (opinion.getOpinion() == 1) {
                    dt.setLiked(true);
                } else if (opinion.getOpinion() == -1) {
                    dt.setDisliked(true);
                }
            }

            result.add(dt);
        }

        return result;
    }
}
