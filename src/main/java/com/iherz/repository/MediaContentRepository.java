package com.iherz.repository;

import com.google.gson.JsonArray;

public interface MediaContentRepository {

    JsonArray getMovies(final String filter, final String level);
}
