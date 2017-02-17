package com.iherz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iherz.repository.MediaContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MediaContentServiceImpl implements MediaContentService {

    @Autowired
    private MediaContentRepository mediaContentRepository;

    @Override
    public JsonArray retrieveMedia(final String filter, final String level) {
        JsonArray movies = mediaContentRepository.getMovies(filter, level);
        Collection moviesCollection = new ArrayList();

        movies.forEach(movie -> {
            moviesCollection.add(movie);
            String classification  = movie.getAsJsonObject().get("peg$contentClassification").getAsString();
            if (classification.equals("")) {
                //moviesToReturn.remove(movie);
            }
        });

        //moviesCollection.stream().filter(movie -> isThisMovieCensored(movie));

        return null;
    }

    private Boolean isThisMovieCensored(final JsonObject movie) {
        return "Censored".equals(movie.getAsJsonObject().get("peg$contentClassification").getAsString());
    }
}
