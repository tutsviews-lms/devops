package com.devops.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by IBS on 31/01/2017.
 */

@Configuration
public class I18NConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
     ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
     resourceBundleMessageSource.setBasename("classpath:i18n/messages");
     // Checks for new messages every  30 minutes
     resourceBundleMessageSource.setCacheMillis(1800);
     return resourceBundleMessageSource;
    }


}

