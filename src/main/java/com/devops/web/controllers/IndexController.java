package com.devops.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IBS on 30/01/2017.
 */
@Controller
public class IndexController {


    @RequestMapping("/")
        public String home(){
        return "index";
        }
}
