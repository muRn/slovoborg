package org.slovob.slovoborg;

import lombok.extern.slf4j.Slf4j;
import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.slovob.slovoborg.opinion.Opinion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultService {
    private DefinitionRepository definitionRepo;

    @Autowired
    public DefaultService(DefinitionRepository definitionRepo) {
        this.definitionRepo = definitionRepo;
    }

    public List<Definition> getDefinitions(String clientIp) {
        log.info("Looking for definitions with " + clientIp + " opinions");
        List<Definition> definitions = definitionRepo.findByApproved(true);
        for (Definition d : definitions) {
            List<Opinion> userOpinions = d.getOpinions()
                    .stream()
                    .filter(x -> x.getIpAddress().equals(clientIp))
                    .collect(Collectors.toList());
            d.setOpinions(userOpinions);
        }

        return definitions;
    }
}
