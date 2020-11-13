package org.slovob.slovoborg.definition;

import lombok.Data;
import org.slovob.slovoborg.opinion.Opinion;
import org.slovob.slovoborg.user.User;
import org.slovob.slovoborg.word.Word;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class DefinitionDto {
    private long id;
    private Word word;
    private String definition;
    private String example;
    private String submittedBy;
    private LocalDate submittedOn;
    private int likes;
    private int dislikes;
    private boolean liked;
    private boolean disliked;

    public static DefinitionDto fromDefinition(Definition definition, User user) {
        DefinitionDto result = new DefinitionDto();

        result.id = definition.getId();
        result.word = definition.getWord();
        result.definition = definition.getDefinition();
        result.example = definition.getExample();
        result.submittedBy = definition.getAuthor().getName();
        result.submittedOn = definition.getSubmittedOn();
        result.likes = definition.getLikes();
        result.dislikes = definition.getDislikes();

        if (user != null) {
            long userId = user.getId();
            Optional<Opinion> opinionOpt = definition.getOpinions()
                    .stream()
                    .filter(x -> x.getUser().getId() == userId)
                    .findFirst();

            opinionOpt.ifPresent(result::setOpinion);
        }

        return result;
    }

    public void setOpinion(Opinion opinion) {
        if (opinion.getOpinion() == 1) {
            liked = true;
        } else if (opinion.getOpinion() == -1) {
            disliked = true;
        }
    }
}
