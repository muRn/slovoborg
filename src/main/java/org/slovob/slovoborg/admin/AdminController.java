package org.slovob.slovoborg.admin;

import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private DefinitionRepository repo;

    @Autowired
    public AdminController(DefinitionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String showDefinitionsToApprove(Model model) {
        List<Definition> definitionsToApprove = repo.findByApproved(false);
        model.addAttribute(definitionsToApprove);
        return "/admin";
    }

    @PostMapping
    public String approveDefinition(long id) {
        Optional<Definition> definitionOpt = repo.findById(id);
        if (!definitionOpt.isPresent()) {
            throw new RuntimeException("Approved definition " + id + " does not exist");
        }

        Definition definition = definitionOpt.get();
        definition.setApproved(true);
        repo.save(definition);
        return "redirect:/admin";
    }
}
