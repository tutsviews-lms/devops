package com.devops.utils;

import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.service.contract.IUserService;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.web.controllers.ForgotmyPasswordController;
import com.devops.web.domain.frontend.BasicAccountPayload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class UserUtils {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserUtils.class);

    private UserUtils() {
        throw new AssertionError("Non instansiable");
    }


    /**
     * Create a user with basic attributes set
     */
    public static User createBasicUser(String userName, String email) {

        User user = new User();
        user.setUsername(userName);
        user.setPassword("secret");
        user.setEmail(email);
        user.setFirstName("Aladin");
        user.setLastName("Zaier");
        user.setPhoneNumber("0606060606");
        user.setCountry("FR");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/123");

        return user;
    }


    public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {
        return request.getScheme()
                + "://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath()
                + ForgotmyPasswordController.CHANGE_PASSWORD_URL
                + "?id="
                + userId +
                "&token="
                + token;
    }

    public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T webUser) {
        User user = new User();

        user.setUsername(webUser.getUsername());
        user.setPassword(webUser.getPassword());
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());
        user.setEmail(webUser.getEmail());
        user.setPhoneNumber(webUser.getPhoneNumber());
        user.setCountry(webUser.getCountry());
        user.setEnabled(true);
        user.setDescription(webUser.getDescription());

        return user;
    }

    public static void createWebMasterUserIfNonExisting(IUserService userService, String webmasterUsername, String webmasterEmail, String webmasterPassword) {

        if (!userService.existUserWithUserNameOrEmail(webmasterUsername, webmasterEmail)) {

            User user = UserUtils.createBasicUser(webmasterUsername, webmasterEmail);
            user.setPassword(webmasterPassword);

            Set<UserRole> userRoles = new HashSet<>();
            UserRole userRole = new UserRole(user, new Role(RoleEnum.ADMIN));
            userRoles.add(userRole);
            LOG.info("Creating user with value {} as username", user.getUsername());
            userService.createUser(user, PlanEnum.PRO, userRoles);
            LOG.info("User with value {} as username created", user.getUsername());

        }


    }

}