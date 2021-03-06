package com.devops.integration.Repository;

import com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.PasswordResetToken;
import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.persistence.repositories.PasswordResetTokenRepository;
import com.devops.backend.persistence.repositories.PlanRepository;
import com.devops.backend.persistence.repositories.RoleRepository;
import com.devops.backend.persistence.repositories.UserRepository;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.utils.UserUtils;

import org.junit.Assert;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */
public abstract class AbstractRepositoryIntegrationTest extends AbstractTest{


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    protected int expirationTimeInMinutes;





    protected Plan createBasicPlan(PlanEnum planEnum){
        return new Plan(planEnum);
    }

    protected   Role createBasicRole(RoleEnum roleEnum){
        return new Role(roleEnum);
    }


    protected User createUser(String userName, String email){

        Plan basicPlan = createBasicPlan(PlanEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser(userName,email);
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

    protected User createUser(TestName testName) {
        return createUser(testName.getMethodName(), testName.getMethodName() + "@tutsviews.com");
    }

    protected PasswordResetToken createPasswordResetToken(String token, User user, LocalDateTime now) {

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, expirationTimeInMinutes);
        passwordResetTokenRepository.save(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getId());
        return passwordResetToken;

    }
}
