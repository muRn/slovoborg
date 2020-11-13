package org.slovob.slovoborg.definition;

import org.slovob.slovoborg.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/definition")
public class DefinitionController {
    private DefinitionService service;

    @Autowired
    public DefinitionController(DefinitionService service) {
        this.service = service;
    }

    @PostMapping
    public String saveDefinition(Definition definition, @AuthenticationPrincipal User user) {
        definition.setSubmittedOn(LocalDate.now());
        definition.setAuthor(user);
        service.saveDefinition(definition);
        return "redirect:/";
    }
}
