package com.devops.integration.service;

import com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.PasswordResetToken;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.persistence.repositories.PasswordResetTokenRepository;
import com.devops.backend.service.contract.IUserService;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.utils.UserUtils;

import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */
public class AbstractServiceIntegrationTest extends AbstractTest {

    @Value("${token.expiration.length.minutes}")
    int expirationInMinutes;

    @Autowired
    protected IUserService iUserService ;

    @Autowired
    protected PasswordResetTokenRepository passwordResetTokenRepository;

    protected User createUser(TestName testName) {
        User basicUser = UserUtils.createBasicUser(testName.getMethodName(),testName.getMethodName()+"@tutsviews.fr");
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, new Role(RoleEnum.BASIC));
        userRoles.add(userRole);

        basicUser = iUserService.createUser(basicUser, PlanEnum.BASIC,userRoles);
        return basicUser;
    }

    protected PasswordResetToken createPasswordResetToken(User user, String token){

        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemUTC());
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, localDateTime, expirationInMinutes);
        return passwordResetTokenRepository.save(passwordResetToken);
    }
}
