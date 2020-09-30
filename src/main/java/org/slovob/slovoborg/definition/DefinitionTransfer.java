package org.slovob.slovoborg.definition;

import lombok.Data;

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
}
