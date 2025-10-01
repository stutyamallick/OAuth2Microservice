package com.learning.resource_server_boi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieBoxOffice {

    private String title;
    private Integer budget;
    private Integer worldwideGross;
    private Integer indiaNett;
    private String boxOfficeVerdict;
}
