package com.devops.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
@Configuration
@EnableJpaRepositories("com.devops.backend.persistence.repositories")
@EntityScan("com.devops.backend.persistence.domain.backend")
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/application-prod.properties")
public class ApplicationConfig {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    @Value("${aws.s3.profile}")
    private String awsProfileName;

    @Value("${stripe.test.private.key}")
    private String stripeDevKey;

    @Bean
    public AmazonS3Client s3Client(){

        LOG.error("the aws profile name is {} ",awsProfileName);



        AWSCredentials credentials = new ProfileCredentialsProvider(awsProfileName).getCredentials();
        AmazonS3Client s3Client = new AmazonS3Client(credentials);
        Region region = Region.getRegion(Regions.EU_CENTRAL_1);
        s3Client.setRegion(region);
        return s3Client;
    }


    @Bean
    public String stripeKey(){
        return stripeDevKey;
    }
}















