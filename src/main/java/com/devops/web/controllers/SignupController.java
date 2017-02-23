package com.devops.web.controllers;

import com.devops.enums.PlanEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ALadin Zaier PC IBS on 23/02/2017.
 */
@Controller
public class SignupController {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    private static final String SIGNUP_VIEW_NAME = "/registration/signup";
    public static final String SIGNUP_URL_MAPPING = "/signup";
    private static final String SIGNUP_PAYLOAD_MODEL_KEY = "payload";

    @GetMapping(SIGNUP_URL_MAPPING)
    public String getSignupPage(@RequestParam("planId") int planId){

        if (planId != PlanEnum.BASIC.getId() && planId != PlanEnum.PRO.getId()) {
            throw new IllegalArgumentException();
        }

        return SIGNUP_VIEW_NAME;
    }
}
