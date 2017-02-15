package com.devops;

import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.service.IUserService;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.utils.UserUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DevopsApplication extends SpringBootServletInitializer implements CommandLineRunner {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(DevopsApplication.class);

    @Value("${application.name}")
    private String applicationName;

    @Autowired
    IUserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DevopsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        LOG.info("Application Name : {}", applicationName);

        String userName = "Aladin";
        String email ="aladin@tutsviews.com";

        if (!userService.existUserWithUserNameOrEmail(userName,email)) {

        User basicUser = UserUtils.createBasicUser(userName,email);
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, new Role(RoleEnum.BASIC));
        userRoles.add(userRole);
        LOG.info("Creating user with value {} as username", basicUser.getUsername());
        User user = userService.createUser(basicUser, PlanEnum.BASIC, userRoles);
        LOG.info("User with value {} as username created", basicUser.getUsername());

        }

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DevopsApplication.class);


    }
}
