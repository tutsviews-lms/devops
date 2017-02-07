package com.devops.backend.service;

import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.persistence.repositories.PlanRepository;
import com.devops.backend.persistence.repositories.RoleRepository;
import com.devops.backend.persistence.repositories.UserRepository;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User createUser(User user, PlanEnum planEnum, Set<UserRole> userRoles){

        Plan plan = new Plan(planEnum);

        if(!planRepository.exists(planEnum.getId())){
            planRepository.save(plan);
        }

        user.setPlan(plan);

        for (UserRole ur : userRoles) {
            roleRepository.save(ur.getRole());
        }

        user.getUserRoles().addAll(userRoles);

        user = userRepository.save(user);

        return user;




    }

}
