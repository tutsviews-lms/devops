package com.devops.backend.service;

import com.devops.backend.service.contract.IStripeService;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ALadin Zaier PC IBS on 28/02/2017.
 */
@Service
public class StripeService implements IStripeService {

    @Autowired
    private String stripeKey;
    
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(StripeService.class);

    @Override
    public String createACustomer(String cardNumber, String cardCode, String cardMonth, String cardYear) {


        Stripe.apiKey = stripeKey;

        String stripeCustomerId = null;

        //Create a token

        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", cardMonth);
        cardParams.put("exp_year", cardYear);
        cardParams.put("cvc", cardCode);
        tokenParams.put("card", cardParams);

        try {

            Token token = Token.create(tokenParams);

            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("source", token.getId());

            // ^ obtained with Stripe.js
            Customer customer = Customer.create(customerParams);

            stripeCustomerId = customer.getId();

        } catch (AuthenticationException e) {
            LOG.error("An Authentification exception has occured while creating the stripe custormer ",e);
            throw new com.devops.exceptions.StripeException();
        } catch (InvalidRequestException e) {
            LOG.error("An invalid request exception has occured while creating the stripe custormer ",e);
            throw new com.devops.exceptions.StripeException();
        } catch (APIConnectionException e) {
            LOG.error("An api connection exception has occured while creating the stripe custormer ",e);
            throw new com.devops.exceptions.StripeException();
        } catch (CardException e) {
            LOG.error("A card exception has occured while creating the stripe custormer ",e);
            throw new com.devops.exceptions.StripeException();
        } catch (APIException e) {
            LOG.error("An api exception has occured while creating the stripe custormer ",e);
            throw new com.devops.exceptions.StripeException();
        }


        return stripeCustomerId;
    }




}
