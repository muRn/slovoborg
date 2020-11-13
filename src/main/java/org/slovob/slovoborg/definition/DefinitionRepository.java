package org.slovob.slovoborg.definition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefinitionRepository extends JpaRepository<Definition, Long> {
    List<Definition> findByApproved(boolean approved);
}
