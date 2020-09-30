package org.slovob.slovoborg.definition;

import lombok.Data;
import lombok.ToString;
import org.slovob.slovoborg.opinion.Opinion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
//@ToString
public class Definition {
    @Id
    @GeneratedValue
    private long id;
    private String word;
    private String definition;
    private String example;
    private String submittedBy;
    private LocalDate submittedOn;
    private int likes;
    private int dislikes;
    private boolean approved;

    @OneToMany(mappedBy = "definition")
    private List<Opinion> opinions;

    @PrePersist
    void submittedOn() {
        submittedOn = LocalDate.now();
    }
}
