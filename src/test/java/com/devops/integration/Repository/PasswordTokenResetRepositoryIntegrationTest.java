package com.devops.integration.Repository;

import com.devops.backend.persistence.domain.backend.PasswordResetToken;
import com.devops.backend.persistence.domain.backend.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */

public class PasswordTokenResetRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {


    @Rule
    public TestName testName = new TestName();



    @Before
    public void init() {
        Assert.assertFalse(expirationTimeInMinutes == 0);
    }




    @Test
    public void testTokenExpirationLength() throws Exception {

        User user = createUser(testName);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());


        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        String token = UUID.randomUUID().toString();

        LocalDateTime expectedTime = now.plusMinutes(expirationTimeInMinutes);

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);

        LocalDateTime actualTime = passwordResetToken.getExpiryDate();
        Assert.assertNotNull(actualTime);
        Assert.assertEquals(expectedTime, actualTime);
    }




    @Test
    public void testFindTokenByTokenValue() throws Exception {

        User user = createUser(testName);

        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        createPasswordResetToken(token, user, now);

        PasswordResetToken retrievedPasswordResetToken = passwordResetTokenRepository.findByToken(token);
        Assert.assertNotNull(retrievedPasswordResetToken);
        Assert.assertNotNull(retrievedPasswordResetToken.getId());
        Assert.assertNotNull(retrievedPasswordResetToken.getUser());

    }


    @Test
    public void testDeleteToken() throws Exception {

        User user = createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);
        long tokenId = passwordResetToken.getId();
        passwordResetTokenRepository.delete(tokenId);

        PasswordResetToken shouldNotExistToken = passwordResetTokenRepository.findOne(tokenId);
        Assert.assertNull(shouldNotExistToken);

    }




    @Test
    public void testCascadeDeleteFromUserEntity() throws Exception {

        User user = createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);
        Assert.assertNotNull(passwordResetToken.getId());

        userRepository.delete(user.getId());

        Set<PasswordResetToken> shouldBeEmpty = passwordResetTokenRepository.findAllByUserId(user.getId());
        Assert.assertTrue(shouldBeEmpty.isEmpty());


    }



    @Test
    public void testMultipleTokensAreReturnedWhenQueringByUserId() throws Exception {

        User user = createUser(testName);
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        String token1 = UUID.randomUUID().toString();
        String token2 = UUID.randomUUID().toString();
        String token3 = UUID.randomUUID().toString();

        Set<PasswordResetToken> tokens = new HashSet<>();
        tokens.add(createPasswordResetToken(token1, user, now));
        tokens.add(createPasswordResetToken(token2, user, now));
        tokens.add(createPasswordResetToken(token3, user, now));

        passwordResetTokenRepository.save(tokens);

        User founduser = userRepository.findOne(user.getId());

        Set<PasswordResetToken> actualTokens = passwordResetTokenRepository.findAllByUserId(founduser.getId());
        Assert.assertTrue(actualTokens.size() == tokens.size());

        Assert.assertEquals(actualTokens, tokens);

    }





}
