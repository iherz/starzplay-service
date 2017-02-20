package com.iherz.web;

import com.google.gson.JsonArray;
import com.iherz.repository.MediaContentRepository;
import com.iherz.service.FilterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MediaContentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MediaContentController mediaContentController;
    @Mock
    private MediaContentRepository mediaContentRepository;
    @Mock
    private FilterService filterService;

    @Before
    public void setup() {
        initMocks(this);
        when(mediaContentRepository.getMovies()).thenReturn(new JsonArray());
        when(filterService.retrieveMediaByCensoring(new JsonArray(), new String())).thenReturn(new ArrayList<>());
        ReflectionTestUtils.setField(mediaContentController, "mediaContentRepository", mediaContentRepository);
        ReflectionTestUtils.setField(mediaContentController, "filterService", filterService);
        this.mockMvc = standaloneSetup(mediaContentController).build();
    }

    @Test
    public void getMediaContentWithCensoringFilterTest() throws Exception {
        this.mockMvc.perform(get("/media?filter=censoring&level=censored")
                .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk());
    }

    @Test
    public void getMediaContentWithoutFilterTest() throws Exception {
        this.mockMvc.perform(get("/media?filter=&level=censored")
                .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk());
    }

}
