package com.devops.integration.service;

import com.devops.AbstractTest;
import com.devops.backend.service.I18NService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class I18NServiceTest extends AbstractTest{


    @Autowired
    I18NService i18NService;

    @Test
    public void i18nService_should_return_right_message_from_an_idMessage() {
        String messageId = "index.main.collout";
        String messageValue = "Bootstrap starter template";
        Assert.assertEquals(messageValue, i18NService.getMessage(messageId));
    }
}
