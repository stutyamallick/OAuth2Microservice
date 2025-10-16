package com.learning.resource_server_imbd.service;

import com.learning.resource_server_imbd.entity.MovieEntity;
import com.learning.resource_server_imbd.model.Movie;
import com.learning.resource_server_imbd.model.MovieBoxOffice;
import com.learning.resource_server_imbd.model.MovieRating;
import com.learning.resource_server_imbd.model.MoviesResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    @Autowired
    RestTemplate restTemplate;

    public MoviesResponseModel getAllMoviesWithRatingAndBoxOffice(Authentication authentication){

        String token = getTokenInfo(authentication);

        List<MovieEntity> allMovies = getJustMovie();
        List<MovieRating> allMoviesRating = new ArrayList<>();
        List<MovieBoxOffice> allMovieBoxOffice = new ArrayList<>();


        try {
            allMoviesRating = movieRatings(token);
        }catch(Exception exception){
            System.out.println("EXCEPTION while retrieving rating");
        }
        try {
            allMovieBoxOffice = movieBoxOffices(token);
        } catch (Exception exception){
            System.out.println("EXCEPTION while retrieving box office");
        }

        List<Movie> movies = new ArrayList<>();

        for (MovieEntity movie: allMovies){

            MovieRating movieRating = allMoviesRating.stream().filter(
                    obj -> obj.getTitle().equals(movie.getTitle())).toList().get(0);

            MovieBoxOffice movieBoxOffice = allMovieBoxOffice.stream().filter(
                    obj -> obj.getTitle().equals(movie.getTitle())).toList().get(0);

            movies.add(
                    new Movie(
                            movie.getId(), movie.getTitle(), movie.getDirector(), movie.getActors(), movie.getYearOfRelease(), movie.getGenre(), movieRating, movieBoxOffice
                    )
            );

        }

        return new MoviesResponseModel(null, true, movies);
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

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<MovieRating>> response = restTemplate.exchange(
                "lb://RESOURCE-SERVER-ROTTENTOMATO/api/rottenTomato/rating/all",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<MovieRating>>() {}
        );

        return response.getBody();
    }

    private List<MovieBoxOffice> movieBoxOffices(String token){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<MovieBoxOffice>> response = restTemplate.exchange(
                "lb://RESOURCE-SERVER-BOI/api/boi/business/all",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<MovieBoxOffice>>() {}
        );

        return response.getBody();
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
