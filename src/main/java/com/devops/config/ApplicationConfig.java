package com.devops.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
@Configuration
@EnableJpaRepositories("com.devops.backend.persistence.repositories")
@EntityScan("com.devops.backend.persistence.domain.backend")
@EnableTransactionManagement
public class ApplicationConfig {
}
