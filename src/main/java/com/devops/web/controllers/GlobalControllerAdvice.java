package com.devops.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ALadin Zaier PC IBS on 28/02/2017.
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalControllerAdvice {

    private final static String GLOBAL_ERROR_VIEW_NAME = "error/genericError";

    @ExceptionHandler(Exception.class)
    public ModelAndView handelError(HttpServletRequest request ,Exception e) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e.getClass().getSimpleName());
        mav.addObject("message", e.getMessage());
        mav.addObject("url", request.getRequestURL());
        mav.addObject("timestamp", LocalDateTime.now(Clock.systemUTC()));

        mav.setViewName(GLOBAL_ERROR_VIEW_NAME);

        return (mav);
    }

}