package com.devops.integration;

import com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.repositories.PlanRepository;
import com.devops.backend.persistence.repositories.RoleRepository;
import com.devops.backend.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class RepositoriesIntegrationTest extends AbstractTest {


    private static final int BASIC_PLAN_ID = 1;

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
        Plan retrievedPlan = planRepository.findOne(basicPlan.getId());
        Assert.assertNotNull(retrievedPlan);

    }






    private Plan createBasicPlan(){
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("basic");

        return plan;

    }

}
