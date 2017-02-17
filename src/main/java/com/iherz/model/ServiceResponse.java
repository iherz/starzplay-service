package com.iherz.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServiceResponse {

    private FilterType filterType;
    private List<Movie> movies;
}
