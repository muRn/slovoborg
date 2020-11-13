package org.slovob.slovoborg.opinion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    @Query("select o from Opinion o join o.definition d join o.user u where d.id=?1 and u.id=?2")
    Optional<Opinion> findByDefinitionIdAndUserId(long definitionId, long userId);
}
