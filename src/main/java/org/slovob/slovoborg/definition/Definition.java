package org.slovob.slovoborg.definition;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDate;

@Data
@Entity
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

    @PrePersist
    void submittedOn() {
        submittedOn = LocalDate.now();
    }
}
