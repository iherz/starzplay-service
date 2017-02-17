package com.iherz.web;

import com.google.gson.JsonArray;
import com.iherz.model.FilterType;
import com.iherz.model.ServiceResponse;
import com.iherz.repository.MediaContentRepository;
import com.iherz.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaContentController {

    @Autowired
    private MediaContentRepository mediaContentRepository;

    @Autowired
    private FilterService filterService;

    @RequestMapping(value = "media", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> media(final @RequestParam String filter, final @RequestParam String level) {
        JsonArray movies = mediaContentRepository.getMovies();
        ServiceResponse serviceResponse = new ServiceResponse();
        if ("censoring".equals(filter)) {
            serviceResponse.setFilterType(FilterType.CENSORING);
            serviceResponse.setMovies(filterService.retrieveMediaByCensoring(movies, level));
        } else {
            serviceResponse.setFilterType(FilterType.NO_FILTER);
            serviceResponse.setMovies(filterService.retrieveNoFilter(movies));
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
