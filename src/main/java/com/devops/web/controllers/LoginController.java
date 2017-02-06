package com.devops.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by ALadin Zaier PC IBS on 05/02/2017.
 */

@Controller
public class LoginController {

    private static final String LOGIN_VIEW_NAME = "user/login";

    @GetMapping("/login")
    public String getLogin(){
        return LOGIN_VIEW_NAME;
    }
}
