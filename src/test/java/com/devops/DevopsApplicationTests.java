package com.devops;

import com.devops.integration.Repository.UserRepositoryIntegrationTest;
import com.devops.integration.Repository.PasswordTokenResetRepositoryIntegrationTest;
import com.devops.integration.service.I18NServiceTest;
import com.devops.integration.service.UserServiceIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(Suite.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@ActiveProfiles(profiles={"test"})
@Suite.SuiteClasses({ UserRepositoryIntegrationTest.class, I18NServiceTest.class, com.devops.integration.Repository.UserServiceIntegrationTest.class,
        UserServiceIntegrationTest.class, PasswordTokenResetRepositoryIntegrationTest.class})
public class DevopsApplicationTests {


}
