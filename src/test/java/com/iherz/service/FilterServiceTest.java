package com.iherz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iherz.model.Movie;
import com.iherz.model.MovieVersion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

public class FilterServiceTest {

    private JsonArray jsonArray;

    @InjectMocks
    private FilterServiceImpl filterService;

    @Before
    public void setUp() {
        initMocks(this);
        generateJsonArrayFromString();
    }

    @Test
    public void censoredMoviesTest() {
        List<Movie> moviesResultLit = filterService.retrieveMediaByCensoring(jsonArray, "censored");
        assertEquals(moviesResultLit.size(), 4);
        moviesResultLit.forEach(movie -> {
            List<MovieVersion> movieVersionList = movie.getMovieVersionList();
            assertEquals(movieVersionList.size(), 1);
            if ("Censored".equals(movie.getClassification())) {
                movieVersionList.forEach(movieVersion -> {
                    assertTrue(movieVersion.getGuid().endsWith("C"));
                });
            }
        });

    }

    @Test
    public void uncensoredMoviesTest() {
        List<Movie> moviesResultLit = filterService.retrieveMediaByCensoring(jsonArray, "uncensored");
        assertEquals(moviesResultLit.size(), 4);
        moviesResultLit.forEach(movie -> {
            List<MovieVersion> movieVersionList = movie.getMovieVersionList();
            assertEquals(movieVersionList.size(), 1);
            if ("Censored".equals(movie.getClassification())) {
                movieVersionList.forEach(movieVersion -> {
                    assertFalse(movieVersion.getGuid().endsWith("C"));
                });
            }
        });

    }

    private JsonArray generateJsonArrayFromString() {
        jsonArray = new JsonArray();

        JsonObject movieMock = new JsonObject();
        fillJsonMock("Oz : The Great And Powerful", "", movieMock);
        JsonArray mediaArray = new JsonArray();
        mediaArray.add(fillMediaMock("media1", "OZTHEGREATANDPOWERFULY2013MFR"));
        movieMock.add("media", mediaArray);
        jsonArray.add(movieMock);

        movieMock = new JsonObject();
        fillJsonMock("Vualto: An Unexpected DRM Journey. Extra type", "Uncensored", movieMock);
        mediaArray = new JsonArray();
        mediaArray.add(fillMediaMock("media1", "VUALTOTESTEXTRA"));
        movieMock.add("media", mediaArray);
        jsonArray.add(movieMock);

        movieMock = new JsonObject();
        fillJsonMock("How to Train Your Dragon", "Censored", movieMock);
        mediaArray = new JsonArray();
        mediaArray.add(fillMediaMock("media1", "HOWTOTRAINYOURDRAGONY2010M"));
        mediaArray.add(fillMediaMock("media2", "HOWTOTRAINYOURDRAGONY2010MC"));
        movieMock.add("media", mediaArray);
        jsonArray.add(movieMock);

        movieMock = new JsonObject();
        fillJsonMock("Think Like A Man", "Censored", movieMock);
        mediaArray = new JsonArray();
        mediaArray.add(fillMediaMock("media1", "OZTHEGREATANDPOWERFULY2013MFR"));
        mediaArray.add(fillMediaMock("media2", "THINKLIKEAMANY2012MC"));
        movieMock.add("media", mediaArray);
        jsonArray.add(movieMock);

        return jsonArray;
    }

    private JsonObject fillMediaMock(String id, String guid) {
        JsonObject media = new JsonObject();
        media.addProperty("id",id);
        media.addProperty("guid",guid);
        return media;
    }

    private JsonObject fillJsonMock(String title, String classification, JsonObject movie) {
        movie.addProperty("title", title);
        movie.addProperty("peg$contentClassification", classification);
        return movie;
    }
}
