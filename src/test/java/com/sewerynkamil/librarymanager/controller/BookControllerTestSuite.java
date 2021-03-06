package com.sewerynkamil.librarymanager.controller;

import com.google.gson.Gson;
import com.sewerynkamil.librarymanager.config.security.AuthenticationEntryPointJwt;
import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.dto.BookDto;
import com.sewerynkamil.librarymanager.mapper.BookMapper;
import com.sewerynkamil.librarymanager.service.BookService;
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
@WebMvcTest(BookController.class)
@MockBeans({
        @MockBean(UserService.class),
        @MockBean(TokenUtilJwt.class),
        @MockBean(AuthenticationEntryPointJwt.class)
})
public class BookControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    @Test
    @WithMockUser
    public void testGetAllBooksWithLazyLoading() throws Exception {
        // Given
        List<Book> bookList = new ArrayList<>();
        when(bookService.findAllBooksWithLazyLoading(anyInt(), anyInt())).thenReturn(bookList);

        // When & Then
        mockMvc.perform(get("/v1/books")
                .param("offset", "10")
                .param("limit", "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testGetAllBooksByTitleStartsWithIgnoreCase() throws Exception {
        // Given
        List<Book> bookList = new ArrayList<>();
        String test = "tit";
        when(bookService.findAllBooksByTitleStartsWithIgnoreCase(anyString())).thenReturn(bookList);

        // When & Then
        mockMvc.perform(get("/v1/books/titles/" + test)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testGetAllBooksByAuthorStartsWithIgnoreCase() throws Exception {
        // Given
        List<Book> bookList = new ArrayList<>();
        String test = "aut";
        when(bookService.findAllBooksByAuthorStartsWithIgnoreCase(anyString())).thenReturn(bookList);

        // When & Then
        mockMvc.perform(get("/v1/books/authors/" + test)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testGetAllBooksByCategoryStartsWithIgnoreCase() throws Exception {
        // Given
        List<Book> bookList = new ArrayList<>();
        String test = "cat";
        when(bookService.findAllBooksByCategoryStartsWithIgnoreCase(anyString())).thenReturn(bookList);

        // When & Then
        mockMvc.perform(get("/v1/books/categories/" + test)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testGetOneBook() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.THRILLER), 2001);
        book.setId(1L);

        BookDto bookDto = new BookDto("Author", "Title", Category.categoryFactory(Category.THRILLER), 2001);

        when(bookService.findOneBook(book.getId())).thenReturn(book);
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);

        // When & Then
        mockMvc.perform(get("/v1/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.title", is("Title")));
    }

    @Test
    @WithMockUser
    public void testIsBookExist() throws Exception {
        // Given
        String title = "Title";
        when(bookService.isBookExist(title)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/v1/books/exist/" + title)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @WithMockUser
    public void testCountBooks() throws Exception {
        // Given
        when(bookService.countBooks()).thenReturn(5L);

        // When & Then
        mockMvc.perform(get("/v1/books/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testSaveNewBook() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.ROMANCE), 2001);
        book.setId(1L);

        BookDto bookDto = new BookDto("Author", "Title", Category.categoryFactory(Category.ROMANCE), 2001);

        when(bookService.saveNewBook(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBook(any(BookDto.class))).thenReturn(book);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookDto);

        // When & Then
        mockMvc.perform(post("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testUpdateBook() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2001);
        book.setId(1L);

        BookDto updatedBook = new BookDto("Author_Updated", "Title_Updated", Category.categoryFactory(Category.FABLE), 2001);

        when(bookService.updateBook(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.mapToBook(any(BookDto.class))).thenReturn(book);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedBook);

        // When & Then
        mockMvc.perform(put("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.author", is("Author_Updated")))
                .andExpect(jsonPath("$.title", is("Title_Updated")));
    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testDeleteBook() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.COMIC), 2001);
        book.setId(1L);

        when(bookService.saveNewBook(book)).thenReturn(book);

        // When & Then
        mockMvc.perform(delete("/v1/books/")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}