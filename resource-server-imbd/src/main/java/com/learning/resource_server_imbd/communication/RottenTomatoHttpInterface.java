package com.learning.resource_server_imbd.communication;

import com.learning.resource_server_imbd.model.MovieRating;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface RottenTomatoHttpInterface {

    @GetExchange("/api/rottenTomato/rating/all")
    List<MovieRating> movieRatings();
}
