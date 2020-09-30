package org.slovob.slovoborg;

import lombok.extern.slf4j.Slf4j;
import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.slovob.slovoborg.definition.DefinitionTransfer;
import org.slovob.slovoborg.opinion.Opinion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DefaultService {
    private DefinitionRepository definitionRepo;

    @Autowired
    public DefaultService(DefinitionRepository definitionRepo) {
        this.definitionRepo = definitionRepo;
    }

    public List<DefinitionTransfer> getDefinitions(String clientIp) {
        log.info("Looking for definitions with " + clientIp + " opinions");
        List<Definition> definitions = definitionRepo.findByApproved(true);
        List<DefinitionTransfer> result = new ArrayList<>();
        for (Definition d : definitions) {
            DefinitionTransfer dt = new DefinitionTransfer(d);
            if (!d.getOpinions().isEmpty()) {
                Optional<Opinion> opinion = d.getOpinions().stream()
                        .filter(x -> x.getIpAddress().equals(clientIp))
                        .findAny();
                opinion.ifPresent(dt::setOpinion);
            }

            result.add(dt);
        }

        return result;
    }
}
