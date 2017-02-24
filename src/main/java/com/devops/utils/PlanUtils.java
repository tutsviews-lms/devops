package com.devops.utils;

import com.devops.backend.service.PlanService;
import com.devops.enums.PlanEnum;

/**
 * Created by ALadin Zaier PC IBS on 23/02/2017.
 */
public class PlanUtils {


    public static void createNonExistingPlans(PlanService planService){

        if (planService.findPlanById(PlanEnum.BASIC.getId())==null) {
        planService.createPlan(PlanEnum.BASIC.getId());
        }

        if (planService.findPlanById(PlanEnum.PRO.getId())==null) {
        planService.createPlan(PlanEnum.PRO.getId());
        }


    }
}
