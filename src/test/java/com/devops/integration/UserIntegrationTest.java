package com.devops.integration;

import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class UserIntegrationTest extends AbstractIntegrationTest {



    @Rule
    public TestName testName = new TestName();


    @Before
    public void init(){
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(planRepository);

    }

    @Test
    public void test_create_new_plan(){

        Plan basicPlan = createBasicPlan(PlanEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findOne(PlanEnum.BASIC.getId());
        Assert.assertNotNull(retrievedPlan);

    }

    @Test
    public void test_create_new_role(){

        Role basicRole = createBasicRole(RoleEnum.BASIC);
        roleRepository.save(basicRole);
        Role retrievedRole = roleRepository.findOne(RoleEnum.BASIC.getId());
        Assert.assertNotNull(retrievedRole);

    }

    @Test
    public void test_new_user() throws Exception{

        String userName = testName.getMethodName();
        String email = testName.getMethodName()+"@tutsviews.com";

        User basicUser = createUser(userName,email);

        User newlyCreatedUser = userRepository.findOne(basicUser.getId());

        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
        for (UserRole userRoleElement :newlyCreatedUserRoles) {
            Assert.assertNotNull(userRoleElement.getRole());
            Assert.assertNotNull(userRoleElement.getRole().getId());
        }

        userRepository.delete(basicUser.getId());
    }

    @Test
    public void test_deleteUser()throws Exception{

        String userName = testName.getMethodName();
        String email = testName.getMethodName()+"@tutsviews.com";

        User basicUser = createUser(userName,email);
        userRepository.delete(basicUser.getId());

    }

/* Private methods */





}
