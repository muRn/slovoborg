package org.slovob.slovoborg.word;

import lombok.Data;
import org.slovob.slovoborg.definition.Definition;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Word {
    @Id
    @GeneratedValue
    private long id;
    private String word;

    @OneToMany(mappedBy = "word")
    private List<Definition> definitions;
}
