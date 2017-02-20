package com.iherz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.iherz.model.Movie;
import com.iherz.model.MovieVersion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {

    List<Movie> moviesToReturn;

    @Override
    public List<Movie> retrieveMediaByCensoring(final JsonArray movies, final String level) {
        moviesToReturn = new ArrayList<>();
        movies.forEach(movieJson -> {
            moviesToReturn.add(getMovieParametersForCensoringFilter(movieJson, level));
        });
        return moviesToReturn;
    }

    @Override
    public List<Movie> retrieveNoFilter(JsonArray movies) {
        moviesToReturn = new ArrayList<>();
        movies.forEach(movieJson -> {
            moviesToReturn.add(getAllMovieParameters(movieJson));
        });
        return moviesToReturn;
    }

    private Movie getMovieParametersForCensoringFilter(final JsonElement movieJson, final String level) {
        Movie movie = fillMovieParameters(movieJson);
        if (isThisMovieCensored(movieJson)) {
            movie.setClassification("Censored");
            movie.setMovieVersionList(movie.getMovieVersionList().stream().filter(movieVersion ->
                    thisMediaHasToBeAdded(movieVersion.getGuid(), level)).collect(Collectors.toList()));
        } else {
            movie.setClassification("Uncensored/NoClassified");
        }
        return movie;
    }

    private Movie getAllMovieParameters(final JsonElement movieJson) {
        return fillMovieParameters(movieJson);
    }

    private Boolean isThisMovieCensored(final JsonElement movie) {
        return "Censored".equals(movie.getAsJsonObject().get("peg$contentClassification").getAsString());
    }

    private Boolean thisMediaHasToBeAdded(final String guid, final String level) {
        return (guid.endsWith("C") && "censored".equals(level.toLowerCase())
                || !guid.endsWith("C") && "uncensored".equals(level.toLowerCase())
                || level.isEmpty());
    }

    private Movie fillMovieParameters(final JsonElement movie) {
        Movie movieToAdd = new Movie();
        List<MovieVersion> movieVersionList = new ArrayList<>();
        JsonArray medias = movie.getAsJsonObject().get("media").getAsJsonArray();
        medias.forEach(media -> {
            MovieVersion movieVersion = new MovieVersion();
            movieVersion.setId(media.getAsJsonObject().get("id").getAsString());
            movieVersion.setGuid(media.getAsJsonObject().get("guid").getAsString());
            movieVersionList.add(movieVersion);
        });
        movieToAdd.setMovieVersionList(movieVersionList);
        movieToAdd.setTitle(movie.getAsJsonObject().get("title").getAsString());
        movieToAdd.setClassification("");
        return movieToAdd;
    }
}
