package org.slovob.slovoborg.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
        Optional<Word> wordOpt = repo.findById(id);
        wordOpt.ifPresent(word -> model.addAttribute(word.getDefinitions()));
        return "index";
    }
}
