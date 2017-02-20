package com.devops;

import com.devops.integration.RepositoriesIntegrationTest;
import com.devops.integration.UserServiceIntegrationTest;
import com.devops.service.I18NServiceTest;
import com.devops.service.UserServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(Suite.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@Suite.SuiteClasses({ RepositoriesIntegrationTest.class, I18NServiceTest.class, UserServiceIntegrationTest.class,
        UserServiceTest.class})
public class DevopsApplicationTests {


}
