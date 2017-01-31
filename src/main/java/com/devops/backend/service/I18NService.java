package com.devops.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by IBS on 31/01/2017.
 */
@Service
public class I18NService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String messageId){
        Locale local = LocaleContextHolder.getLocale();
        return getMessage(messageId,local);
    }

    public String getMessage(String messageId, Locale local){
        return messageSource.getMessage(messageId,null,local);
    }



}
