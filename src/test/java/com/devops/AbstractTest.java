package com.devops;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by ALadin Zaier PC IBS on 06/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@ActiveProfiles(profiles={"test"})
public abstract class AbstractTest {

    /**
     * The application logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTest.class);

}


