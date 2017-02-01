package com.devops.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IBS on 01/02/2017.
 */
@Controller
public class CopyController {

    @RequestMapping("/about")
    public String getAbout(){
        return "copy/about";
    }
}
