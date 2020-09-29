package org.slovob.slovoborg.opinion;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Data
@Entity
public class Opinion {
    @Id
    @GeneratedValue
    private long id;
    private long definitionId;
    private String ipAddress;
    private int opinion;
    private LocalDateTime updatedAt;

    @PrePersist
    void updatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
