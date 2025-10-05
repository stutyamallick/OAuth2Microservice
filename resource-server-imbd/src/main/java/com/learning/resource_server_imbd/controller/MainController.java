package com.learning.resource_server_imbd.controller;

import com.learning.resource_server_imbd.model.MoviesResponseModel;
import com.learning.resource_server_imbd.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/api/imbd/movies/all")
    public MoviesResponseModel getAllMovies(Authentication authentication){
        return mainService.getAllMoviesWithRatingAndBoxOffice(authentication);
    }

}
