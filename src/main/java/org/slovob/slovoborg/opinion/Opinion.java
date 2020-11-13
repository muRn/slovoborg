package org.slovob.slovoborg.opinion;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.user.User;

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

    @ManyToOne
    private User user;
    private int opinion;
    private LocalDateTime updatedAt;

    @PrePersist
    void updatedAt() {
        updatedAt = LocalDateTime.now();
    }

    public Opinion(int opinion, long definitionId, User user) {
        this.opinion = opinion;

        this.user = user;

        Definition d = new Definition();
        d.setId(definitionId);
        definition = d;
    }
}
