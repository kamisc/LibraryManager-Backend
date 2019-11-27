package com.sewerynkamil.librarymanager.controller;

import com.google.gson.Gson;
import com.sewerynkamil.librarymanager.config.security.AuthenticationEntryPointJwt;
import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.dto.SpecimenDto;
import com.sewerynkamil.librarymanager.mapper.SpecimenMapper;
import com.sewerynkamil.librarymanager.service.SpecimenService;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@WebMvcTest(SpecimenController.class)
@MockBeans({
        @MockBean(UserService.class),
        @MockBean(TokenUtilJwt.class),
        @MockBean(AuthenticationEntryPointJwt.class)
})
public class SpecimenControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecimenService specimenService;

    @MockBean
    private SpecimenMapper specimenMapper;

    @Test
    @WithMockUser(roles = "Admin")
    public void testGetAllSpecimensForOneBook() throws Exception {
        // Given
        List<Specimen> specimenList = new ArrayList<>();
        when(specimenService.findAllSpecimensForOneBookByBookId(anyLong())).thenReturn(specimenList);

        // When & Then
        mockMvc.perform(get("/v1/specimens/")
                .param("bookId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testGetAllSpecimensForOneBookWithStatus() throws Exception {
        // Given
        List<Specimen> specimenList = new ArrayList<>();
        when(specimenService.findAllSpecimensForOneBookByStatusAndBookId(anyString(), anyLong())).thenReturn(specimenList);

        // When & Then
        mockMvc.perform(get("/v1/specimens/1")
                .param("status", "AVAILABLE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testGetOneSpecimen() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.ACTION), 2001);
        Specimen specimen = new Specimen(Status.UNAVAILABLE.getStatus(), "Publisher", 2001, book, 1234567891011L);
        SpecimenDto specimenDto = new SpecimenDto(Status.UNAVAILABLE.getStatus(), "Publisher", 2001, book.getTitle(), 1234567891011L);

        when(specimenService.findOneSpecimen(anyLong())).thenReturn(specimen);
        when(specimenMapper.mapToSpecimenDto(any(Specimen.class))).thenReturn(specimenDto);

        // When & Then
        mockMvc.perform(get("/v1/specimens/get/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", is("Unavailable")))
                .andExpect(jsonPath("$.publisher", is("Publisher")))
                .andExpect(jsonPath("$.bookTitle", is("Title")));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testSaveNewSpecimen() throws Exception {
        // Given
        Book book = new Book("Author", "2Title", Category.categoryFactory(Category.TRAGEDY), 2001);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 1234567891011L);
        SpecimenDto specimenDto = new SpecimenDto(Status.AVAILABLE.getStatus(), "Publisher", 2001, book.getTitle(), 1234567891011L);

        when(specimenService.saveNewSpecimen(any(Specimen.class))).thenReturn(specimen);
        when(specimenMapper.mapToSpecimen(any(SpecimenDto.class))).thenReturn(specimen);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(specimenDto);

        // When & Then
        mockMvc.perform(post("/v1/specimens")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testUpdateSpecimen() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2001);
        book.setId(1L);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2005, book, 1234567891111L);

        SpecimenDto updatedSpecimen = new SpecimenDto(Status.RENTED.getStatus(), "Pub", 2005, book.getTitle(), 1234567891111L);

        when(specimenService.updateSpecimen(any(Specimen.class))).thenReturn(specimen);
        when(specimenMapper.mapToSpecimenDto(any(Specimen.class))).thenReturn(updatedSpecimen);
        when(specimenMapper.mapToSpecimen(any(SpecimenDto.class))).thenReturn(specimen);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedSpecimen);

        // When & Then
        mockMvc.perform(put("/v1/specimens")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", is("Rented")))
                .andExpect(jsonPath("$.publisher", is("Pub")));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testDeleteSpecimen() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.DRAMA), 2001);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 1234567891011L);
        specimen.setId(1L);

        when(specimenService.saveNewSpecimen(specimen)).thenReturn(specimen);

        // When & Then
        mockMvc.perform(delete("/v1/specimens/")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}