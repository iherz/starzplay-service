package com.iherz.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iherz.boot.conf.HttpClientLocalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MediaContentRepositoryImpl implements MediaContentRepository {

    private static final String URL = "http://loadtester.aws.playco.com/apps/assignment/content.json";

    @Autowired
    private HttpClientLocalConfig httpClientLocalConfig;

    @Override
    public JsonArray getMovies(String filter, String level) {
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setRequestFactory(httpClientLocalConfig.getFactory());
        String jsonResponse = restTemplate.getForObject(URL, String.class);
        JsonObject jsonObject = new JsonParser().parse(jsonResponse).getAsJsonObject();
        return jsonObject.getAsJsonArray("entries");
    }
}
