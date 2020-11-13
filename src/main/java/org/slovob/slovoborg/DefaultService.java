package org.slovob.slovoborg;

import lombok.extern.slf4j.Slf4j;
import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionDto;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.slovob.slovoborg.user.User;
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

    public List<DefinitionDto> getDefinitions(User user) {
        log.info("Looking for approved definitions" + (user != null ? " with " + user.getName() + " opinions" : ""));
        List<Definition> definitions = definitionRepo.findByApproved(true);
        return definitions.stream()
                .map(x -> DefinitionDto.fromDefinition(x, user))
                .collect(Collectors.toList());
    }
}
