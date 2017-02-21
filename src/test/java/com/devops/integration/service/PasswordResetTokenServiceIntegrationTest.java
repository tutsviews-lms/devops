package com.devops.integration.service;

import com.devops.backend.persistence.domain.backend.PasswordResetToken;
import com.devops.backend.persistence.domain.backend.User;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.UUID;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */
public class PasswordResetTokenServiceIntegrationTest extends AbstractServiceIntegrationTest {

//    findByToken createPasswordResetTokenFromEmail

    @Rule
    public TestName testName = new TestName();

    @Test
    public void findByToken_should_return_a_passwordRestToken_user_with_a_given_token(){

        String token = UUID.randomUUID().toString();
        User user = createUser(testName);
        PasswordResetToken passwordResetToken = createPasswordResetToken(user,token);

        PasswordResetToken retrievedPasswordResetToken = passwordResetTokenService.findByToken(token);

        Assert.assertNotNull(retrievedPasswordResetToken);
        Assert.assertNotNull(retrievedPasswordResetToken.getId());

        Assert.assertEquals(retrievedPasswordResetToken.getId(),passwordResetToken.getId());

    }



//    createPasswordResetTokenFromEmail

    @Test
    public void createPasswordResetTokenFromEmail_should_create_a_passwordResetToken_with_a_valid_email(){

        User user = createUser(testName);
        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenFromEmail(user.getEmail());

        Assert.assertNotNull(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getId());

        Assert.assertNotNull(passwordResetToken.getToken());
        Assert.assertNotNull(passwordResetToken.getExpiryDate());
    }






}
