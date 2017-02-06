package com.devops.config;

import com.devops.backend.service.EmailService;
import com.devops.backend.service.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ALadin Zaier PC IBS on 02/02/2017.
 */
@Configuration
@Profile("dev-local")
@PropertySource("classpath:/application-dev-local.properties")
public class DevelopementLocalConfig {

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }

}
