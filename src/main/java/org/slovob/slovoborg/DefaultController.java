package org.slovob.slovoborg;

import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class DefaultController {
    private DefinitionRepository defRepo;

    @Autowired
    public DefaultController(DefinitionRepository defRepo) {
        this.defRepo = defRepo;
    }

    @GetMapping
    public String showMainPage(Model model) {
        List<Definition> definitions = defRepo.findByApproved(true);
        model.addAttribute(definitions);
        return "index";
    }
}
