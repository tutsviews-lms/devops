package com.devops.integration;

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
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class UserServiceIntegrationTest extends AbstractTest {

    @Autowired
    private IUserService IUserService;

    @Test
    public void testCreateduser(){

        User basicUser = UserUtils.createBasicUser();
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, new Role(RoleEnum.BASIC));
        userRoles.add(userRole);

        User user = IUserService.createUser(basicUser, PlanEnum.BASIC,userRoles);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());



        }
}