package com.devops.backend.persistence.repositories;

import com.devops.backend.persistence.domain.backend.PasswordResetToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */
@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken,Long> {

    PasswordResetToken findByToken(String token);

    Set<PasswordResetToken> findAllByUserId(long userId);
}
