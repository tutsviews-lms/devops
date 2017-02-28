package com.devops.backend.service.contract;

/**
 * Created by ALadin Zaier PC IBS on 28/02/2017.
 */
public interface IStripeService {

    String createACustomer(String cardNumber, String cardCode, String cardMonth, String cardYear);

}
