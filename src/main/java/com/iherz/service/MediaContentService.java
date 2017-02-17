package com.iherz.service;

import com.iherz.model.Movie;
import java.util.List;

public interface MediaContentService {

    List<Movie> retrieveMedia(final String filter, final String level);
}
