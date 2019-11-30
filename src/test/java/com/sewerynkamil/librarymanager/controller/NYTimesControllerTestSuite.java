package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.config.security.AuthenticationEntryPointJwt;
import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesResultsDto;
import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesSection;
import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesTopStoriesDto;
import com.sewerynkamil.librarymanager.service.NYTimesService;
import com.sewerynkamil.librarymanager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@WebMvcTest(NYTimesController.class)
@MockBeans({
        @MockBean(UserService.class),
        @MockBean(TokenUtilJwt.class),
        @MockBean(AuthenticationEntryPointJwt.class),
})
public class NYTimesControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NYTimesService nyTimesService;

    @Test
    @WithMockUser
    public void testGetAllTopStories() throws Exception {
        // Given
        String home = NYTimesSection.HOME.getSection();
        List<NYTimesResultsDto> result = new ArrayList<>();
        NYTimesResultsDto nyTimesResultsDto = new NYTimesResultsDto("home", "title", "url", "by Me", "2005-11-30");
        result.add(nyTimesResultsDto);
        NYTimesTopStoriesDto nyTimesTopStoriesDto = new NYTimesTopStoriesDto(result);

        when(nyTimesService.fetchNYTimesTopStories(anyString())).thenReturn(nyTimesTopStoriesDto);

        // When & Then
        mockMvc.perform(get("/v1/topstories/" + home.toLowerCase())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}