package com.iherz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.iherz.model.Movie;
import com.iherz.model.MovieVersion;
import com.iherz.repository.MediaContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaContentServiceImpl implements MediaContentService {

    @Autowired
    private MediaContentRepository mediaContentRepository;

    @Override
    public List<Movie> retrieveMedia(final String filter, final String level) {
        JsonArray movies = mediaContentRepository.getMovies(filter, level);
        List<Movie> moviesToReturn = new ArrayList();

        movies.forEach(movieJson -> {
            moviesToReturn.add(getMovieParameters(movieJson, level));
        });
        return moviesToReturn;
    }

    private Movie getMovieParameters(final JsonElement movie, final String level) {
        Movie movieToAdd = new Movie();
        List<MovieVersion> movieVersionList = new ArrayList<>();
        JsonArray medias = movie.getAsJsonObject().get("media").getAsJsonArray();
        medias.forEach(media -> {
            MovieVersion movieVersion = new MovieVersion();
            movieVersion.setId(media.getAsJsonObject().get("id").getAsString());
            movieVersion.setGuid(media.getAsJsonObject().get("guid").getAsString());
            if (isThisMovieCensored(movie)) {
                if (thisMediaHasToBeAdded(movieVersion.getGuid(), level)) {
                    movieVersionList.add(movieVersion);
                }
            } else {
                movieVersionList.add(movieVersion);
            }
        });
        movieToAdd.setMovieVersionList(movieVersionList);
        movieToAdd.setTitle(movie.getAsJsonObject().get("title").getAsString());
        return movieToAdd;
    }

    private Boolean isThisMovieCensored(final JsonElement movie) {
        return "Censored".equals(movie.getAsJsonObject().get("peg$contentClassification").getAsString());
    }

    private Boolean thisMediaHasToBeAdded(final String guid, final String level) {
        return (guid.endsWith("C") && "censored".equals(level.toLowerCase())
            || !guid.endsWith("C") && "uncensored".equals(level.toLowerCase()));
    }
}
