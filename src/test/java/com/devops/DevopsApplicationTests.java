package com.devops;

import com.devops.integration.RepositoriesIntegrationTest;
import com.devops.service.I18NServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(Suite.class)
@SpringBootTest
@WebAppConfiguration
@Suite.SuiteClasses({ RepositoriesIntegrationTest.class, I18NServiceTest.class})
public class DevopsApplicationTests {


}
