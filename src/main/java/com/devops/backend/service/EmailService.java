package com.devops.backend.service;

import com.devops.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by ALadin Zaier PC IBS on 02/02/2017.
 */
public interface EmailService {

    /**
     * Sends an email with the content of the Feedback Pojo
     * @param feedbackPojo the feedback Pojo
     */
    public void sendFeedbackEmail(FeedbackPojo feedbackPojo);

    /**
     * Sends an email with the content of the Simple Mail Message pojo
     * @param message the object containig the email content
     */
    public void sendGenericEmailMessage(SimpleMailMessage message);

}
