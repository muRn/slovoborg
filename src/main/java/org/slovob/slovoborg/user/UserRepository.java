package org.slovob.slovoborg.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByNameAndActive(String name, boolean active);

    Optional<User> findByEmailAndActive(String email, boolean active);
}
