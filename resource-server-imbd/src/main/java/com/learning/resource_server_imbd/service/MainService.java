package com.learning.resource_server_imbd.service;

import com.learning.resource_server_imbd.communication.BoiHttpInterface;
import com.learning.resource_server_imbd.communication.RottenTomatoHttpInterface;
import com.learning.resource_server_imbd.entity.MovieEntity;
import com.learning.resource_server_imbd.model.Movie;
import com.learning.resource_server_imbd.model.MovieBoxOffice;
import com.learning.resource_server_imbd.model.MovieRating;
import com.learning.resource_server_imbd.model.MoviesResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final BoiHttpInterface boiHttpInterface;

    private final RottenTomatoHttpInterface rottenTomatoHttpInterface;

    public MoviesResponseModel getAllMoviesWithRatingAndBoxOffice(){

        List<MovieEntity> allMovies = getJustMovie();
        List<MovieRating> allMoviesRating = new ArrayList<>();
        List<MovieBoxOffice> allMovieBoxOffice = new ArrayList<>();


        try {
            allMoviesRating = movieRatings();
        }catch(Exception exception){
            System.out.println("EXCEPTION while retrieving rating");
        }
        try {
            allMovieBoxOffice = movieBoxOffices();
        } catch (Exception exception){
            System.out.println("EXCEPTION while retrieving box office");
            System.out.println(exception.getMessage());
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

    private List<MovieRating> movieRatings(){

        return rottenTomatoHttpInterface.movieRatings();
    }

    private List<MovieBoxOffice> movieBoxOffices(){

        return boiHttpInterface.movieBusiness();
    }
}
