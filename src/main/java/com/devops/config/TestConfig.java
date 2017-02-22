package com.devops.config;

import com.devops.backend.service.EmailService;
import com.devops.backend.service.SmtpEmailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */
@Configuration
@Profile("test")
@PropertySource("classpath:/application-test.properties")
public class TestConfig {

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }

}

