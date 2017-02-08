package com.devops.integration;

import com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.persistence.repositories.PlanRepository;
import com.devops.backend.persistence.repositories.RoleRepository;
import com.devops.backend.persistence.repositories.UserRepository;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.utils.UserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class RepositoriesIntegrationTest extends AbstractTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlanRepository planRepository;


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

        User basicUser = createUser();

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
    }

    @Test
    public void test_deleteUser()throws Exception{

        User basicUser = createUser();
        userRepository.delete(basicUser.getId());

    }

/* Private methods */

    private Plan createBasicPlan(PlanEnum planEnum){
        return new Plan(planEnum);
    }

    private Role createBasicRole(RoleEnum roleEnum){
        return new Role(roleEnum);
    }


    private User createUser(){

        Plan basicPlan = createBasicPlan(PlanEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole = createBasicRole(RoleEnum.BASIC);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser,basicRole);
        userRoles.add(userRole);

        for (UserRole userRoleElement : userRoles){
            roleRepository.save(userRoleElement.getRole());
        }

        basicUser.getUserRoles().addAll(userRoles);

        basicUser = userRepository.save(basicUser);

        return basicUser;
    }




}
