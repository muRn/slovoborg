package org.slovob.slovoborg.definition;

import lombok.extern.slf4j.Slf4j;
import org.slovob.slovoborg.word.Word;
import org.slovob.slovoborg.word.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DefinitionService {
    private DefinitionRepository definitionRepo;
    private WordRepository wordRepo;

    @Autowired
    public DefinitionService(DefinitionRepository definitionRepo, WordRepository wordRepo) {
        this.definitionRepo = definitionRepo;
        this.wordRepo = wordRepo;
    }

    public void saveDefinition(Definition dt) {
        Word word = dt.getWord();
        Optional<Word> wordOpt = wordRepo.findByWord(word.getWord());
        if (wordOpt.isPresent()) {
            log.info("Word " + word.getWord() + " already exists");
            word = wordOpt.get();
        } else {
            log.info("New word " + word.getWord() + " detected, persisting");
            word = wordRepo.save(word);
        }
        dt.setWord(word);
        definitionRepo.save(dt);
    }
}
