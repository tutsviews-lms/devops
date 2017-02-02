package com.devops.web.controllers;

import com.devops.backend.service.EmailService;
import com.devops.web.domain.frontend.FeedbackPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by ALadin Zaier PC IBS on 01/02/2017.
 */

@Controller
public class ContactController {

    /** The application logger */

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);
    
    public static final String FEEDBACK_MODEL_KEY = "feedback";
    public static final String CONTACT_US_VIEW_NAME = "contact/contact";

    @Autowired
    EmailService emailService;

    @ModelAttribute(FEEDBACK_MODEL_KEY)
    public FeedbackPojo getFeedbackPojo(){
        return new FeedbackPojo();
    }

    @GetMapping("/contact")
    public String getContact(Model model){
        return CONTACT_US_VIEW_NAME;
    }

    @PostMapping("/contact")
    public String postContact(@ModelAttribute FeedbackPojo feedbackPojo){
        LOG.info(feedbackPojo.toString());
        emailService.sendFeedbackEmail(feedbackPojo);
        return "redirect:/";
    }

}
