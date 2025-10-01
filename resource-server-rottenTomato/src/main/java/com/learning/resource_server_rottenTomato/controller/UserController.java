package com.learning.resource_server_rottenTomato.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/rottenTomato/user-info")
    public String userInfo(Authentication authentication){

        return authentication != null ? authentication.getName() : "NOT_FOUND";
    }
}
