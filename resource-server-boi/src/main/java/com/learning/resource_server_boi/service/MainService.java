package com.learning.resource_server_boi.service;

import com.learning.resource_server_boi.model.MovieBoxOffice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    public List<MovieBoxOffice> getAllMoviesBusiness(){

        var movie1 = new MovieBoxOffice("Vikram", 135, 414, 247, "ATB");
        var movie2 = new MovieBoxOffice("Kesari Chapter 2", 100, 145, 92, "AVERAGE");
        var movie3 = new MovieBoxOffice("Pathaan", 230, 1040, 544, "ATB");
        var movie4 = new MovieBoxOffice("Dunki", 80, 410, 215, "HIT");
        var movie5 = new MovieBoxOffice("Sikander", 200, 180, 100, "FLOP");


        return List.of(movie1, movie2, movie3, movie4, movie5);
    }
}
