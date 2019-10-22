package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.dto.BookDto;
import com.sewerynkamil.librarymanager.mapper.BookMapper;
import com.sewerynkamil.librarymanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RestController
@RequestMapping("/v1/books")
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
}
