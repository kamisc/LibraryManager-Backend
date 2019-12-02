package com.sewerynkamil.librarymanager.controller;

import com.google.gson.Gson;
import com.sewerynkamil.librarymanager.config.security.AuthenticationEntryPointJwt;
import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.dto.RentDto;
import com.sewerynkamil.librarymanager.mapper.RentMapper;
import com.sewerynkamil.librarymanager.service.RentService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@WebMvcTest(RentController.class)
@MockBeans({
        @MockBean(UserService.class),
        @MockBean(TokenUtilJwt.class),
        @MockBean(AuthenticationEntryPointJwt.class)
})
public class RentControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentService rentService;

    @MockBean
    private RentMapper rentMapper;

    @Test
    @WithMockUser(roles = "Admin")
    public void testGetAllRentsWithLazyLoading() throws Exception {
        // Given
        List<Rent> rentList = new ArrayList<>();
        when(rentService.findAllRentsWithLazyLoading(anyInt(), anyInt())).thenReturn(rentList);

        // When & Then
        mockMvc.perform(get("/v1/rents")
                .param("offset", "2")
                .param("limit", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testGetAllRentsByUserId() throws Exception {
        // Given
        List<Rent> rentList = new ArrayList<>();
        when(rentService.findAllRentsByUserId(anyLong())).thenReturn(rentList);

        // When & Then
        mockMvc.perform(get("/v1/rents/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testGetAllRentsByBookTitleStartsWithIgnoreCase() throws Exception {
        // Given
        List<Rent> rentList = new ArrayList<>();
        String bookTitle = "Example";
        when(rentService.findAllRentsByBookTitleStartsWithIgnoreCase(bookTitle)).thenReturn(rentList);

        // When & Then
        mockMvc.perform(get("/v1/rents/titles/" + bookTitle)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testGetAllRentsByUserEmailStartsWithIgnoreCase() throws Exception {
        // Given
        List<Rent> rentList = new ArrayList<>();
        String email = "example@gmail.com";
        when(rentService.findAllRentsByUserEmailStartsWithIgnoreCase(anyString())).thenReturn(rentList);

        // When & Then
        mockMvc.perform(get("/v1/rents/emails/" + email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testCountRents() throws Exception {
        // Given
        when(rentService.countRents()).thenReturn(5L);

        // When & Then
        mockMvc.perform(get("/v1/rents/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testIsRentExistBySpecimenId() throws Exception {
        // Given
        Long id = 1L;
        when(rentService.isRentExistBySpecimenId(id)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/v1/rents/exist/id/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testIsRentExistBySpecimenBookTitle() throws Exception {
        // Given
        String title = "Title";
        when(rentService.isRentExistBySpecimenBookTitle(title)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/v1/rents/exist/title/" + title)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @WithMockUser
    public void testRentBook() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.HUMOR), 2001);
        book.setId(1L);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 1234567891011L);
        specimen.setId(2L);
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER.getRole());
        user.setId(3L);

        Rent rent = new Rent(specimen, user);
        rent.setId(4L);

        RentDto rentDto = new RentDto(4L, 3L, book.getTitle(), user.getEmail(), rent.getRentDate(), rent.getReturnDate());

        when(rentService.rentBook(anyLong(), anyLong())).thenReturn(rent);
        when(rentMapper.mapToRentDto(any(Rent.class))).thenReturn(rentDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(rentDto);

        // When & Then
        mockMvc.perform(post("/v1/rents/" + user.getId())
                .param("specimenId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser
    public void testProlongationBook() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.AUTOBIOGRAPHY), 2001);
        book.setId(1L);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 1234567891011L);
        specimen.setId(2L);
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER.getRole());
        user.setId(3L);

        Rent rent = new Rent(specimen, user);
        rent.setId(4L);

        RentDto updatedRent = new RentDto(4L, 3L, book.getTitle(), user.getEmail(), rent.getRentDate(), LocalDate.of(2020, 1, 3));

        when(rentService.prolongationRent(anyLong(), anyLong())).thenReturn(rent);
        when(rentMapper.mapToRentDto(any(Rent.class))).thenReturn(updatedRent);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedRent);

        // When & Then
        mockMvc.perform(put("/v1/rents/" + user.getId())
                .param("specimenId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnDate", is("2020-01-03")));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testDeleteRent() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.BIOGRAPHY), 2001);
        book.setId(1L);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 1234567891011L);
        specimen.setId(2L);
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER.getRole());
        user.setId(3L);

        Rent rent = new Rent(specimen, user);
        rent.setId(4L);

        when(rentService.rentBook(anyLong(), anyLong())).thenReturn(rent);

        // When & Then
        mockMvc.perform(delete("/v1/rents/")
                .param("id", "4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}