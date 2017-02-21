package com.devops.backend.persistence.repositories;

import com.devops.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    /**
     * Returns a User given a userName
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    public User findUserByEmail(String email);

    public User save(User user);
}
