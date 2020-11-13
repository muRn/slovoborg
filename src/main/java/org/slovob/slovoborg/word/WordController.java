package org.slovob.slovoborg.word;

import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionDto;
import org.slovob.slovoborg.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/word")
public class WordController {
    private WordRepository repo;

    @Autowired
    public WordController(WordRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String showWordDefinitions(@RequestParam long id, Model model, @AuthenticationPrincipal User user) {
        List<Definition> definitions = repo.findApprovedDefinitionsByWordId(id);
        List<DefinitionDto> definitionDtoList = definitions.stream()
                .map(x -> DefinitionDto.fromDefinition(x, user))
                .collect(Collectors.toList());
        model.addAttribute(definitionDtoList);
        return "index";
    }
}
