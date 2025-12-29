package com.learning.oauth2_server_netflix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/ott/sent")
    public String sentOTT(){
        return "ott-sent";
    }

}
