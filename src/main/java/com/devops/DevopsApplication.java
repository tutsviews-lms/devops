package com.devops;

import com.devops.backend.service.PlanService;
import com.devops.backend.service.contract.IUserService;
import com.devops.utils.PlanUtils;
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

    @Autowired
    PlanService planService;

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

        UserUtils.createWebMasterUserIfNonExisting(userService,webmasterUsername,webmasterEmail,webmasterPassword);
        PlanUtils.createNonExistingPlans(planService);

    }




    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DevopsApplication.class);
    }




}
