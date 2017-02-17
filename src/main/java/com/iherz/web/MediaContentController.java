package com.iherz.web;

import com.google.gson.JsonArray;
import com.iherz.service.MediaContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/")
public class MediaContentController {

    @Autowired
    private MediaContentService mediaContentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String media() {
        return "";
    }

    @RequestMapping(value = "media", method = RequestMethod.GET)
    public JsonArray media(final @RequestParam String filter, final @RequestParam String level) {
        //TODO Meter objeto de respuesta http
        return mediaContentService.retrieveMedia(filter, level);
    }
}
