package com.learning.resource_server_rottenTomato.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRating {

    private float criticsRating;
    private float audienceRating;
    private String mostHelpfulReview;
    private String mostHelpfulReviewBy;
}
