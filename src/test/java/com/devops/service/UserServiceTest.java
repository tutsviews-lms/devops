package com.devops.service;

import com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.service.IUserService;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.utils.UserUtils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 15/02/2017.
 */
public class UserServiceTest extends AbstractTest {


    @Autowired
    private IUserService iUserService ;





    @Test
    public void existUserWithUserNameOrEmail_should_return_true_with_existing_email_or_userName(){

        String userName = "userServiceTest";
        String  email = "userServiceTest"+"@tutsviews.com";

        User basicUser = UserUtils.createBasicUser(userName,email);
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, new Role(RoleEnum.BASIC));
        userRoles.add(userRole);

        basicUser = iUserService.createUser(basicUser, PlanEnum.BASIC,userRoles);

        boolean exist1 = iUserService.existUserWithUserNameOrEmail("userServiceTest","");
        boolean exist2 = iUserService.existUserWithUserNameOrEmail("","userServiceTest@tutsviews.com");

        Assert.assertTrue(exist1);
        Assert.assertTrue(exist2);

        iUserService.deleteUser(basicUser);
    }


    @Test
    public void existUserWithUserNameOrEmail_should_return_false_with_non_existing_email_or_userName(){

        boolean exist = iUserService.existUserWithUserNameOrEmail("985478542157","985478542155");
        Assert.assertFalse(exist);


    }

}
