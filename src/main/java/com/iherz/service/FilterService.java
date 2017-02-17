package com.iherz.service;

import com.google.gson.JsonArray;
import com.iherz.model.Movie;

import java.util.List;

public interface FilterService {

    List<Movie> retrieveMediaByCensoring(final JsonArray movies, final String level);

    List<Movie> retrieveNoFilter(final JsonArray movies);
}
