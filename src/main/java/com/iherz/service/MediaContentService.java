package com.iherz.service;

import com.google.gson.JsonArray;

public interface MediaContentService {

    JsonArray retrieveMedia(final String filter, final String level);
}
