package com.devops.backend.service;

import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.repositories.PlanRepository;
import com.devops.enums.PlanEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ALadin Zaier PC IBS on 23/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class PlanService {

@Autowired
    PlanRepository planRepository;

    public Plan findPlanById(int id){
        return planRepository.findOne(id);
    }

    @Transactional
    public Plan  createPlan(int id){

        if (id == PlanEnum.BASIC.getId()) {
        return planRepository.save(new Plan(PlanEnum.BASIC));
        }else

        if (id == PlanEnum.PRO.getId()) {
        return planRepository.save(new Plan(PlanEnum.PRO));
        }

        else {
            throw new IllegalArgumentException();
        }

    }

}
