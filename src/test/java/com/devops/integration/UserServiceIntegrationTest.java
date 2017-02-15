package com.devops.integration;

import  com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.service.IUserService;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.utils.UserUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class UserServiceIntegrationTest extends AbstractTest {

    @Autowired
    private IUserService iUserService;

    @Rule
    public TestName testName = new TestName();

    @Test
    public void testCreateUser(){

        String userName = testName.getMethodName();
        String  email = testName.getMethodName()+"@tutsviews.com";

        User basicUser = UserUtils.createBasicUser(userName,email);
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, new Role(RoleEnum.BASIC));
        userRoles.add(userRole);

        User user = iUserService.createUser(basicUser, PlanEnum.BASIC,userRoles);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        Boolean value = iUserService.deleteUser(user);

        Assert.assertTrue(value);

        }



}
