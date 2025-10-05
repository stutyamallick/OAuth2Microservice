package com.learning.resource_server_imbd.service;

import com.learning.resource_server_imbd.entity.MovieEntity;
import com.learning.resource_server_imbd.model.MovieBoxOffice;
import com.learning.resource_server_imbd.model.MovieRating;
import com.learning.resource_server_imbd.model.MoviesResponseModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class MainService {

    public MoviesResponseModel getAllMoviesWithRatingAndBoxOffice(Authentication authentication){

        String token = getTokenInfo(authentication);
        String ratingStr = "";
        String boxOfficeStr = "";

        try {
            List<MovieRating> allMoviesRating = movieRatings(token);
            ratingStr = String.valueOf(allMoviesRating.get(0).getAudienceRating());
        }catch(Exception exception){
            ratingStr = exception.getMessage();
        }
        try {
            List<MovieBoxOffice> allMovieBoxOffice = movieBoxOffices(token);
            boxOfficeStr = allMovieBoxOffice.get(0).getBoxOfficeVerdict();
        } catch (Exception exception){
            boxOfficeStr = exception.getMessage();
        }
        List<MovieEntity> allMovies = getJustMovie();

        return new MoviesResponseModel(token + "; " + ratingStr + "; " + boxOfficeStr, true, null);
    }

    private List<MovieEntity> getJustMovie(){

        var movie1 = new MovieEntity(1, "Vikram", "Lokesh Kanagaraj", "Kamal Hasan, Vijay Sethupathi, Fahad Fasil", 2022, "ACTION");
        var movie2 = new MovieEntity(1, "Kesari Chapter 2", "Karan Tyagi", "Akshay Kumar", 2025, "DRAMA");
        var movie3 = new MovieEntity(1, "Pathaan", "Sid Anand", "SRK, Deepika, John Abraham", 2023, "ACTION");
        var movie4 = new MovieEntity(1, "Dunki", "Raj Kumar Hirani", "SRK, Vicky Kusal, Tapsee Pannu", 2023, "COMEDY/DRAMA");
        var movie5 = new MovieEntity(1, "Sikander", "A R Murugadoss", "Salman Khan", 2025, "ACTION");


        return List.of(movie1, movie2, movie3, movie4, movie5);
    }

    private List<MovieRating> movieRatings(String token){

        RestClient restClient = RestClient.builder()
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(token))
                .baseUrl("lb://RESOURCE-SERVER-ROTTENTOMATO").build();

        return restClient
                .get()
                .uri("/api/rottenTomato/rating/all")
                .retrieve()
                .body(new ParameterizedTypeReference<List<MovieRating>>() {});
    }

    private List<MovieBoxOffice> movieBoxOffices(String token){

        RestClient restClient = RestClient.builder()
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(token))
                .baseUrl("lb://RESOURCE-SERVER-BOI").build();

        return restClient
                .get()
                .uri("/api/boi/business/all")
                .retrieve()
                .body(new ParameterizedTypeReference<List<MovieBoxOffice>>() {});
    }

    public String getTokenInfo(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {

            if (oauthToken.getPrincipal() instanceof Jwt jwt)
                return jwt.getTokenValue();
            else
                return "";

        } else if (authentication.getPrincipal() instanceof Jwt jwt)
            return jwt.getTokenValue();

        return "";
    }
}
