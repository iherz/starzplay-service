package com.iherz.repository;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

public class MediaContentRepositoryIntegrationTest {

    @InjectMocks
    private MediaContentRepositoryImpl mediaContentRepository;

    @Test
    public void getMoviesIntegrationTest() {
        initMocks(this);
        assertNotNull(mediaContentRepository.getMovies());
    }
}
