package org.slovob.slovoborg.opinion;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slovob.slovoborg.definition.Definition;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Opinion {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Definition definition;
    private String ipAddress;
    private int opinion;
    private LocalDateTime updatedAt;

    @PrePersist
    void updatedAt() {
        updatedAt = LocalDateTime.now();
    }

    public Opinion(OpinionTransfer ot, String ipAddress) {
        opinion = ot.getOpinion();
        this.ipAddress = ipAddress;
        Definition d = new Definition();
        d.setId(ot.getDefinitionId());
        definition = d;
    }
}
