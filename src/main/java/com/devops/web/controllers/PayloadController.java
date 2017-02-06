package com.devops.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by ALadin Zaier PC IBS on 06/02/2017.
 */
@Controller
public class PayloadController {

    private static final String PAYLOAD_VIEW_NAME = "/payload/payload";

    @GetMapping("/payload")
    public String getPayload(){
        return PAYLOAD_VIEW_NAME;
    }
}
