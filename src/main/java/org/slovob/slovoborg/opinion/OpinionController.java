package org.slovob.slovoborg.opinion;

import org.slovob.slovoborg.definition.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/opinion")
public class OpinionController {
    private DefinitionRepository repo;

    @Autowired
    public OpinionController(DefinitionRepository repo) {
        this.repo = repo;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void processOpinion(@RequestBody Opinion o) {
        if (o.getOpinion().equals("like")) {
            repo.like(o.getDefinitionId());
        } else {
            repo.dislike(o.getDefinitionId());
        }
    }
}
