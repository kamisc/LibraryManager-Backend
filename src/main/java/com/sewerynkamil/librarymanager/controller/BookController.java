package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.domain.exceptions.BookExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.BookNotExistException;
import com.sewerynkamil.librarymanager.dto.BookDto;
import com.sewerynkamil.librarymanager.mapper.BookMapper;
import com.sewerynkamil.librarymanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1/books")
public class BookController {
    private BookService bookService;

    private BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookMapper.mapToBookDtoList(bookService.findAllBooks());
    }

    @GetMapping("/titles/{title}")
    public List<BookDto> getAllBooksByTitleStartsWithIgnoreCase(@PathVariable String title) {
        return bookMapper.mapToBookDtoList(bookService.findAllBooksByTitleStartsWithIgnoreCase(title));
    }

    @GetMapping("/authors/{author}")
    public List<BookDto> getAllBooksByAuthorStartsWithIgnoreCase(@PathVariable String author) {
        return bookMapper.mapToBookDtoList(bookService.findAllBooksByAuthorStartsWithIgnoreCase(author));
    }

    @GetMapping("/categories/{category}")
    public List<BookDto> getAllBooksByCategoryStartsWithIgnoreCase(@PathVariable String category) {
        return bookMapper.mapToBookDtoList(bookService.findAllBooksByCategoryStartsWithIgnoreCase(category));
    }

    @GetMapping("/{id}")
    public BookDto getOneBook(@PathVariable Long id) throws BookNotExistException {
        return bookMapper.mapToBookDto(bookService.findOneBook(id));
    }

    @GetMapping(value = "/exist/{title}")
    public boolean isBookExist(@PathVariable String title) {
        return bookService.isBookExist(title);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveNewBook(@RequestBody BookDto bookDto) throws BookExistException {
        bookService.saveNewBook(bookMapper.mapToBook(bookDto));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBook(@RequestBody BookDto bookDto) throws BookNotExistException {
        return bookMapper.mapToBookDto(bookService.updateBook(bookMapper.mapToBook(bookDto)));
    }

    @DeleteMapping
    public void deleteBook(@RequestParam Long id) throws BookNotExistException {
        bookService.deleteBook(bookService.findOneBook(id));
    }
}