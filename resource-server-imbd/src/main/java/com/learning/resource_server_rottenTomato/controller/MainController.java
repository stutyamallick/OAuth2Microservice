package com.learning.resource_server_rottenTomato.controller;

import com.learning.resource_server_rottenTomato.model.MoviesResponseModel;
import com.learning.resource_server_rottenTomato.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/api/imbd/movies/all")
    public MoviesResponseModel getAllMovies(){
        return mainService.getAllMoviesWithRatingAndBoxOffice();
    }
}
