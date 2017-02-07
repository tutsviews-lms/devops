package com.devops.integration;

import com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.persistence.repositories.PlanRepository;
import com.devops.backend.persistence.repositories.RoleRepository;
import com.devops.backend.persistence.repositories.UserRepository;
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


    private static final int BASIC_PLAN_ID = 1;
    private static final int BASIC_ROLE_ID = 1;

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

        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findOne(BASIC_PLAN_ID);
        Assert.assertNotNull(retrievedPlan);

    }

    @Test
    public void test_create_new_role(){

        Role basicRole = createBasicRole();
        roleRepository.save(basicRole);
        Role retrievedRole = roleRepository.findOne(BASIC_ROLE_ID);
        Assert.assertNotNull(retrievedRole);

    }

    @Test
    public void test_new_user(){

        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        User basicUser = createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole = createBasicRole();

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = createUserRole(basicUser,basicRole);
        userRoles.add(userRole);

        for (UserRole userRoleElement : userRoles){
            roleRepository.save(userRoleElement.getRole());
        }

        basicUser.getUserRoles().addAll(userRoles);

        basicUser = userRepository.save(basicUser);
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



/* Private methods */

    private Plan createBasicPlan(){
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("basic");

        return plan;
    }

    private Role createBasicRole(){
        Role role = new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("basic");

        return role;
    }

    private User createBasicUser(){

        User user = new User();
        user.setUsername("Aladin");
        user.setPassword("secret");
        user.setEmail("secret@gmail.com");
        user.setFirstName("Aladin");
        user.setLastName("Zaier");
        user.setPhoneNumber("0606060606");
        user.setCountry("FR");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/123");

        return user;
    }

    private UserRole createUserRole(User user, Role role){
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        return userRole;
    }

}
