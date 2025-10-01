package com.learning.resource_server_boi.controller;

import com.learning.resource_server_boi.model.MovieBoxOffice;
import com.learning.resource_server_boi.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/api/boi/business/all")
    public List<MovieBoxOffice> movieBusiness(){

        return mainService.getAllMoviesBusiness();
    }
}
