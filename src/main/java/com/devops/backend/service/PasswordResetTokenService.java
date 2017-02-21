package com.devops.backend.service;

import com.devops.backend.persistence.domain.backend.PasswordResetToken;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.repositories.PasswordResetTokenRepository;
import com.devops.backend.persistence.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class PasswordResetTokenService {

    @Value("${token.expiration.length.minutes}")
    private int expirationInMinutes;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);

    /**
     * Retrieve a Password Rest Token from a given Token
     */
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    /**
     * Creates a Password Reset Token With a given email
     * @param email
     * @return
     */
    @Transactional
    public PasswordResetToken createPasswordResetTokenFromEmail(String email) {
        PasswordResetToken passwordResetToken = null;

        User user = userRepository.findUserByEmail(email);
        if (!(user == null)) {
            String token = UUID.randomUUID().toString();
            LocalDateTime localDateTime = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new PasswordResetToken(token, user, localDateTime, expirationInMinutes);
            passwordResetTokenRepository.save(passwordResetToken);
            LOG.info("Successful created PasswordResetToken {} for user {} .", token, user.getUsername());
        }
        LOG.warn("cannot fing user with email {}.", email);
        return passwordResetToken;
    }
}
