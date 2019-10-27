package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Kamil Seweryn
 */

@Component
public class BookMapper {
    public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getAuthor(),
                book.getTitle(),
                book.getCategory(),
                book.getYearOfFirstPublication(),
                book.getIsbn());
    }

    public Book mapToBook(final BookDto bookDto) {
        Book book = new Book(
                bookDto.getAuthor(),
                bookDto.getTitle(),
                bookDto.getCategory(),
                bookDto.getYearOfFirstPublication(),
                bookDto.getIsbn());
        book.setId(bookDto.getId());
        return book;
    }

    public List<BookDto> mapToBookDtoList(final List<Book> bookList) {
        return bookList.stream()
                .map(book -> new BookDto(
                        book.getAuthor(),
                        book.getTitle(),
                        book.getCategory(),
                        book.getYearOfFirstPublication(),
                        book.getIsbn()))
                .collect(Collectors.toList());
    }
}