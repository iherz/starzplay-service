package com.iherz.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MediaContentRepositoryImpl implements MediaContentRepository {

    private static final String URL = "http://loadtester.aws.playco.com/apps/assignment/content.json";

    @Override
    @Cacheable("movies")
    public JsonArray getMovies() {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(URL, String.class);
        JsonObject jsonObject = new JsonParser().parse(jsonResponse).getAsJsonObject();
        return jsonObject.getAsJsonArray("entries");
    }
}
