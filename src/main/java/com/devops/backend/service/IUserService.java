package com.devops.backend.service;

import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.enums.PlanEnum;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 13/02/2017.
 */
public interface IUserService {
    @Transactional
    User createUser(User user, PlanEnum planEnum, Set<UserRole> userRoles);
}
