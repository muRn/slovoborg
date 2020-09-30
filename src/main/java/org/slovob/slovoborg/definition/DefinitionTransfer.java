package org.slovob.slovoborg.definition;

import lombok.Data;
import org.slovob.slovoborg.opinion.Opinion;

@Data
public class DefinitionTransfer extends Definition {
    private boolean liked;
    private boolean disliked;

    public DefinitionTransfer(Definition d) {
        setId(d.getId());
        setWord(d.getWord());
        setDefinition(d.getDefinition());
        setExample(d.getExample());
        setSubmittedBy(d.getSubmittedBy());
        setSubmittedOn(d.getSubmittedOn());
        setLikes(d.getLikes());
        setDislikes(d.getDislikes());
    }

    public void setOpinion(Opinion o) {
        int opinion = o.getOpinion();
        if (opinion == 1) {
            liked = true;
        } else if (opinion == -1) {
            disliked = true;
        }
    }
}
