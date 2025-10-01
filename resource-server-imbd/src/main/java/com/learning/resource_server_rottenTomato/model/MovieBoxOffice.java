package com.learning.resource_server_rottenTomato.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieBoxOffice {

    private Integer budget;
    private Integer worldwideGross;
    private Integer indiaNett;
    private Integer openingDay;
    private Integer firstWeekend;
    private Integer firstWeek;
    private String boxOfficeVerdict;
}
