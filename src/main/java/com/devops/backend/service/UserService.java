package com.devops.backend.service;

import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.persistence.repositories.PlanRepository;
import com.devops.backend.persistence.repositories.RoleRepository;
import com.devops.backend.persistence.repositories.UserRepository;
import com.devops.backend.service.contract.IUserService;
import com.devops.enums.PlanEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Override
    @Transactional
    public User createUser(User user, PlanEnum planEnum, Set<UserRole> userRoles){

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

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

    @Transactional
    public Boolean deleteUser(User user) {

        if (userRepository.findOne(user.getId())==null) {
            return false;
        }else {
            userRepository.delete(user.getId());

            return true;
        }
    }

    public Boolean existUserWithUserNameOrEmail(String userName, String email) {

        if (!(userRepository.findUserByUsername(userName) ==null)) { return true; }

        if (!(userRepository.findUserByEmail(email) ==null)) { return true; }

        return false;
    }

    @Transactional
    public User upadateUserPassword(long userId, String newPassword) {
        User user = userRepository.findOne(userId);

        if (user != null) {
            user.setPassword(newPassword);
            user = userRepository.save(user);
            LOG.info("Password have been succesfully modified ! with value = {}",newPassword);
        }

        return user;
    }


}
