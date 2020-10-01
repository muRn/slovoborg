package org.slovob.slovoborg.word;

import org.slovob.slovoborg.definition.Definition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    public Optional<Word> findByWord(String word);

    @Query("select d from Word w join w.definitions d where d.approved=true and w.id=?1")
    public List<Definition> findApprovedDefinitionsByWordId(long id);
}
