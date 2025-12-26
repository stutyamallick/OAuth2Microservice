package com.learning.resource_server_imbd.communication;

import com.learning.resource_server_imbd.model.MovieBoxOffice;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface BoiHttpInterface {

    @GetExchange("/api/boi/business/all")
    List<MovieBoxOffice> movieBusiness();
}
