package com.devops.backend.service;

import com.devops.web.domain.frontend.Feedback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by ALadin Zaier PC IBS on 02/02/2017.
 */
public abstract class AbstractEmailService implements EmailService {

    @Value("${default.to.address}")
    private String defaultToAddress;

    /***
     * Creates a Simple Mail Message from a feedback Pojo
     * @param feedbackPojo
     * @return
     */
    protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(Feedback feedbackPojo){
       SimpleMailMessage message = new SimpleMailMessage();
       message.setTo(defaultToAddress);
       message.setFrom(feedbackPojo.getEmail());
       message.setSubject("{DevOps Tutsviews]: Feedback recieved from "+feedbackPojo.getFirstName()+" "+feedbackPojo.getLastName() );
       message.setText(feedbackPojo.getFeedback());
       return message;
    }

    @Override
    public void sendFeedbackEmail(Feedback feedbackPojo) {
        sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackPojo(feedbackPojo));
    }
}
