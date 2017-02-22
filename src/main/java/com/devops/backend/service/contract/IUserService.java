package com.devops.backend.service.contract;

import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.enums.PlanEnum;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 13/02/2017.
 */
@Transactional(readOnly = true)
public interface IUserService {

    @Transactional
    User createUser(User user, PlanEnum planEnum, Set<UserRole> userRoles);

    @Transactional
    Boolean deleteUser(User user);

    Boolean existUserWithUserNameOrEmail(String userName, String email);

    @Transactional
    User upadateUserPassword(long userId, String newPassword);

}
