package com.devops.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ALadin Zaier PC IBS on 02/02/2017.
 */
@Configuration
@Profile("prod")
@PropertySource("application-prod.properties")
public class ProductionConfig {
}
