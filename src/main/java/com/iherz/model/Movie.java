package com.iherz.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie {

    private String title;
    private List<MovieVersion> movieVersionList;
}


