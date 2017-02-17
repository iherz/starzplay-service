package com.iherz.web;

import com.google.gson.JsonArray;
import com.iherz.model.Movie;
import com.iherz.service.MediaContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MediaContentController {

    @Autowired
    private MediaContentService mediaContentService;

    @RequestMapping(value = "media", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> media(final @RequestParam String filter, final @RequestParam String level) {
        return new ResponseEntity<>(mediaContentService.retrieveMedia(filter, level), HttpStatus.OK);
    }
}
