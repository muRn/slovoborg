package org.slovob.slovoborg.definition;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDate;

@Entity
@Data
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

    @PrePersist
    void submittedOn() {
        submittedOn = LocalDate.now();
    }
}
