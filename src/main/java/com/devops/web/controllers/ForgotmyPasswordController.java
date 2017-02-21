package com.devops.web.controllers;

import com.devops.backend.persistence.domain.backend.PasswordResetToken;
import com.devops.backend.service.PasswordResetTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by ALadin Zaier PC IBS on 21/02/2017.
 */
@Controller
public class ForgotmyPasswordController {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(ForgotmyPasswordController.class);

    private static final String EMAIL_ADDRESS_VIEW_NAME = "/forgotmypassword/emailform";
    public static final String FORGOT_PASSWORD_URL_MAPPING = "/forgotmypassword";

    private static final String MAIl_SENT_KEY = "mailSent";

    @Autowired
    PasswordResetTokenService passwordResetTokenService;


    @GetMapping(FORGOT_PASSWORD_URL_MAPPING)
    public String getView() {
        return EMAIL_ADDRESS_VIEW_NAME;
    }


    @PostMapping(FORGOT_PASSWORD_URL_MAPPING)
    public String forgotPasswordPost(@ModelAttribute("email") String email, Model model) {

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenFromEmail(email);
        if (passwordResetToken != null) {
            LOG.info("Token {} created for email {}",passwordResetToken.getToken(),email);
        }else {
            LOG.info("Invalid email");
        }

        model.addAttribute(MAIl_SENT_KEY,"true");

        return EMAIL_ADDRESS_VIEW_NAME;

    }


}