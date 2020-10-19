package org.slovob.slovoborg.definition;

import lombok.Data;
import org.slovob.slovoborg.opinion.Opinion;
import org.slovob.slovoborg.word.Word;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Definition {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Word word;

    @Column(length = 2048)
    private String definition;

    @Column(length = 2048)
    private String example;
    private String submittedBy;
    private LocalDate submittedOn;
    private int likes;
    private int dislikes;
    private boolean approved;

    @OneToMany(mappedBy = "definition")
    private List<Opinion> opinions;

    public boolean getLiked() {
        return !opinions.isEmpty() && opinions.get(0).getOpinion() == 1;
    }

    public boolean getDisliked() {
        return !opinions.isEmpty() && opinions.get(0).getOpinion() == -1;
    }

    public void like() {
        likes += 1;
    }

    public void unlike() {
        likes -= 1;
    }

    public void dislike() {
        dislikes += 1;
    }

    public void undislike() {
        dislikes -= 1;
    }
}
