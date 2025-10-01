package com.learning.resource_server_rottenTomato.service;

import com.learning.resource_server_rottenTomato.entity.MovieEntity;
import com.learning.resource_server_rottenTomato.model.MoviesResponseModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    public MoviesResponseModel getAllMoviesWithRatingAndBoxOffice(){

        return null;
    }

    private List<MovieEntity> getJustMovie(){

        var movie1 = new MovieEntity(1, "Vikram", "Lokesh Kanagaraj", "Kamal Hasan, Vijay Sethupathi, Fahad Fasil", 2022, "ACTION");
        var movie2 = new MovieEntity(1, "Kesari Chapter 2", "Karan Tyagi", "Akshay Kumar", 2025, "DRAMA");
        var movie3 = new MovieEntity(1, "Pathaan", "Sid Anand", "SRK, Deepika, John Abraham", 2023, "ACTION");
        var movie4 = new MovieEntity(1, "Dunki", "Raj Kumar Hirani", "SRK, Vicky Kusal, Tapsee Pannu", 2023, "COMEDY/DRAMA");
        var movie5 = new MovieEntity(1, "Sikander", "A R Murugadoss", "Salman Khan", 2025, "ACTION");


        return List.of(movie1, movie2, movie3, movie4, movie5);
    }
}
