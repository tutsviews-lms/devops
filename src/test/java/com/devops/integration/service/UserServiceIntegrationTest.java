package com.devops.integration.service;

import com.devops.backend.persistence.domain.backend.User;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.UUID;

/**
 * Created by ALadin Zaier PC IBS on 15/02/2017.
 */
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {


    @Rule
    public TestName testName= new TestName();



    @Test
    public void existUserWithUserNameOrEmail_should_return_true_with_existing_email_or_userName(){

        User basicUser = createUser(testName);

        boolean exist1 = iUserService.existUserWithUserNameOrEmail(testName.getMethodName(),"");
        boolean exist2 = iUserService.existUserWithUserNameOrEmail("",testName.getMethodName()+"@tutsviews.fr");

        Assert.assertTrue(exist1);
        Assert.assertTrue(exist2);

        iUserService.deleteUser(basicUser);
    }


    @Test
    public void existUserWithUserNameOrEmail_should_return_false_with_non_existing_email_or_userName(){

        boolean exist = iUserService.existUserWithUserNameOrEmail("985478542157","985478542155");
        Assert.assertFalse(exist);


    }


    @Test
    public  void updateUserPassword_should_return_the_user_with_the_new_password(){

        User user = createUser(testName);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        String newPassword = UUID.randomUUID().toString();

        User retrievedUser = iUserService.upadateUserPassword(user.getId(),newPassword);

        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(user.getId(),retrievedUser.getId());
        Assert.assertEquals(retrievedUser.getPassword(),newPassword);




    }

}
