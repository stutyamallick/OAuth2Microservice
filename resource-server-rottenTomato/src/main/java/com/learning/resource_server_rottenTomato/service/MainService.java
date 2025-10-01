package com.learning.resource_server_rottenTomato.service;

import com.learning.resource_server_rottenTomato.model.MovieRating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    public List<MovieRating> getAllMoviesAndRating(){

        var movie1 = new MovieRating("Vikram", 7.9f, 8.3f);
        var movie2 = new MovieRating("Kesari Chapter 2", 8.4f, 8.9f);
        var movie3 = new MovieRating("Pathaan", 8.0f, 8.4f);
        var movie4 = new MovieRating("Dunki", 7.1f, 7.4f);
        var movie5 = new MovieRating("Sikander", 6.2f, 6.9f);

        return List.of(movie1, movie2, movie3, movie4, movie5);
    }

}
