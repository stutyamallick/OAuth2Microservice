package com.learning.resource_server_rottenTomato.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoviesResponseModel {

    private String errorMessage;
    private boolean Status;
    private List<Movie> movies;
}
