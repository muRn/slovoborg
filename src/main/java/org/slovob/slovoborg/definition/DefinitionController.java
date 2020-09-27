package org.slovob.slovoborg.definition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/definition")
public class DefinitionController {
    private DefinitionRepository repo;

    @Autowired
    public DefinitionController(DefinitionRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public String saveDefinition(Definition definition) {
        repo.save(definition);
        return "redirect:/";
    }
}
