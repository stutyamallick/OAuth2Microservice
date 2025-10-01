package com.learning.resource_server_rottenTomato.controller;

import com.learning.resource_server_rottenTomato.model.MovieRating;
import com.learning.resource_server_rottenTomato.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/api/rottenTomato/rating/all")
    public List<MovieRating> movieRatings(){

        return mainService.getAllMoviesAndRating();
    }
}
