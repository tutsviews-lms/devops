package com.devops.web.controllers;

import com.devops.web.domain.frontend.FeedbackPojo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by ALadin Zaier PC IBS on 01/02/2017.
 */

@Controller
public class ContactController {

    public static final String FEEDBACK_MODEL_KEY = "feedback";
    public static final String CONTACT_US_VIEW_NAME = "contact/contact";

    @ModelAttribute(FEEDBACK_MODEL_KEY)
    public FeedbackPojo getFeedbackPojo(){
        return new FeedbackPojo();
    }

    @GetMapping("/contact")
    public String getContact(Model model){
        return CONTACT_US_VIEW_NAME;
    }

}
