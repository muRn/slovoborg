package org.slovob.slovoborg.definition;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DefinitionRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DefinitionRepository definitionRepository;

    @Test
    public void whenFindByApproved_thenReturnAllApproved() {
        // given
        // not using entityManager here because I already have data.sql prepopulating the db with some initial data

        // when
        List<Definition> found = definitionRepository.findByApproved(true);

        // then
        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(0).getSubmittedBy()).isEqualTo("flexitarian");
    }
}
