package org.slovob.slovoborg.opinion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    @Query("select o from Opinion o join o.definition d where d.id=?1 and o.ipAddress=?2")
    public Optional<Opinion> findByDefinitionIdAndIpAddress(long definitionId, String ipAddress);

    @Modifying
    @Transactional
    @Query("update Opinion o set o.opinion=?1 where o.id=?2")
    public void updateOpinion(int opinion, long id);
}
