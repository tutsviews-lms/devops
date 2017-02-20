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

    @Value("${webmaster.username}")
    private String webmasterUsername ;

    @Value("${webmaster.password}")
    private String webmasterPassword;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    public static void main(String[] args) {
        SpringApplication.run(DevopsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        LOG.info("Application Name : {}", applicationName);
        LOG.info("WebMaster username : {}", webmasterUsername);
        LOG.info("Webmaster password : {}", webmasterPassword);
        LOG.info("Webmaster email : {}", webmasterEmail);

        if (!userService.existUserWithUserNameOrEmail(webmasterUsername,webmasterEmail)) {

        User user = UserUtils.createBasicUser(webmasterUsername,webmasterEmail);
            user.setPassword(webmasterPassword);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(user, new Role(RoleEnum.ADMIN));
        userRoles.add(userRole);
        LOG.info("Creating user with value {} as username", user.getUsername());
        userService.createUser(user, PlanEnum.PRO, userRoles);
        LOG.info("User with value {} as username created", user.getUsername());

        }

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DevopsApplication.class);


    }
}
