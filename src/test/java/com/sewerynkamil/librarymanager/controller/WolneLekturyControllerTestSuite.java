package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.client.WolneLekturyClient;
import com.sewerynkamil.librarymanager.config.WolneLekturyConfig;
import com.sewerynkamil.librarymanager.config.security.AuthenticationEntryPointJwt;
import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.dto.wolneLektury.WolneLekturyAudiobookDto;
import com.sewerynkamil.librarymanager.service.UserService;
import com.sewerynkamil.librarymanager.service.WolneLekturyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@WebMvcTest(WolneLekturyController.class)
@MockBeans({
        @MockBean(UserService.class),
        @MockBean(TokenUtilJwt.class),
        @MockBean(AuthenticationEntryPointJwt.class)
})
public class WolneLekturyControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WolneLekturyService wolneLekturyService;

    @Test
    @WithMockUser
    public void testGetAllAudiobooksWithLazyLoading() throws Exception {
        // Given
        List<WolneLekturyAudiobookDto> audiobookList = new ArrayList<>();
        when(wolneLekturyService.fetchWolneLekturyBoardsWithLazyLoading(anyInt(), anyInt())).thenReturn(audiobookList);

        // When & Then
        mockMvc.perform(get("/v1/audiobooks/lazy")
                .param("offset", "10")
                .param("limit", "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testCountAudiobooks() throws Exception {
        // Given
        when(wolneLekturyService.countAudiobooks()).thenReturn(100);

        // When & Then
        mockMvc.perform(get("/v1/audiobooks/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").value(100));
    }
}