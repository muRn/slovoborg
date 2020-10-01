package org.slovob.slovoborg.word;

import org.slovob.slovoborg.definition.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/word")
public class WordController {
    private WordRepository repo;

    @Autowired
    public WordController(WordRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String showWordDefinitions(@RequestParam long id, Model model) {
        List<Definition> definitions = repo.findApprovedDefinitionsByWordId(id);
        model.addAttribute(definitions);
        return "index";
    }
}
