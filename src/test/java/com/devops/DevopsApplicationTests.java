package com.devops;

import com.devops.integration.UserIntegrationTest;
import com.devops.integration.UserServiceIntegrationTest;
import com.devops.integration.PasswordTokenResetIntegrationTest;
import com.devops.service.I18NServiceTest;
import com.devops.service.UserServiceTest;

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
@Suite.SuiteClasses({ UserIntegrationTest.class, I18NServiceTest.class, UserServiceIntegrationTest.class,
        UserServiceTest.class, PasswordTokenResetIntegrationTest.class})
public class DevopsApplicationTests {


}
