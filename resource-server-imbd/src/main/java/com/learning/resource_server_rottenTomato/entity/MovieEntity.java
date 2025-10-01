package com.learning.resource_server_rottenTomato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {

    private Integer id;
    private String title;
    private String director;
    private String actors;
    private Integer yearOfRelease;
    private String genre;
}
