package org.slovob.slovoborg.definition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DefinitionRepository extends JpaRepository<Definition, Long> {
    public List<Definition> findByApproved(boolean approved);

    @Modifying
    @Transactional
    @Query("update Definition d set d.approved=true where d.id=?1")
    public void approve(long id);

    @Modifying
    @Transactional
    @Query("update Definition d set d.likes=d.likes+1 where d.id=?1")
    public void like(long id);

    @Modifying
    @Transactional
    @Query("update Definition d set d.dislikes=d.dislikes+1 where d.id=?1")
    public void dislike(long id);
}
