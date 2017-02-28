package com.devops.integration.service;

import com.devops.backend.service.StripeService;
import com.stripe.Stripe;
import com.stripe.model.Customer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Created by ALadin Zaier PC IBS on 28/02/2017.
 */

public class StripeSerivceIntegrationTest extends AbstractServiceIntegrationTest{

    private final static String TEST_CARD_NUMBER = "4242424242424242";;
    private final static String TEST_CARD_CODE = "123";;
    private final static String TEST_CARD_MONTH = "12";;
    private final static String TEST_CARD_YEAR = String.valueOf(LocalDate.now(Clock.systemUTC()).getYear()+1);

    @Autowired
    StripeService stripeService;

    @Autowired
    String stripeKey;

    @Before
    public void init(){
        Assert.assertNotNull(stripeKey);
        Stripe.apiKey=stripeKey;
    }

    @Test
    public void createCustomer_should_return_a_customerId_not_null() throws Exception{


        String customerId = stripeService.createACustomer(TEST_CARD_NUMBER, TEST_CARD_CODE, TEST_CARD_MONTH, TEST_CARD_YEAR);

        Assert.assertNotNull(customerId);
        LOG.error(customerId);

        Customer customer = Customer.retrieve(customerId);
        customer.delete();

    }
}
