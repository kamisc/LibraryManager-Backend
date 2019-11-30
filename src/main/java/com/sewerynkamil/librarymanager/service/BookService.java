package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.exceptions.BookExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.BookNotExistException;
import com.sewerynkamil.librarymanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Kamil Seweryn
 */

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooksWithLazyLoading(final int offset, final int limit) {
        return bookRepository.findAll().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Book> findAllBooksByTitleStartsWithIgnoreCase(final String title) {
        return bookRepository.findByTitleStartsWithIgnoreCase(title);
    }

    public List<Book> findAllBooksByAuthorStartsWithIgnoreCase(final String author) {
        return bookRepository.findByAuthorStartsWithIgnoreCase(author);
    }

    public List<Book> findAllBooksByCategoryStartsWithIgnoreCase(final String category) {
        return bookRepository.findByCategoryStartsWithIgnoreCase(category);
    }

    public Book findOneBook(final Long id) throws BookNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(BookNotExistException::new);
        if(!bookRepository.existsByTitle(book.getTitle())) {
            throw new BookNotExistException();
        }
        return book;
    }

    public Book saveNewBook(final Book book) throws BookExistException {
        if(bookRepository.existsByTitle(book.getTitle())) {
            throw new BookExistException();
        }
        return bookRepository.save(book);
    }

    public Book updateBook(final Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(final Book book) throws BookNotExistException {
        if(!bookRepository.existsByTitle(book.getTitle())) {
            throw new BookNotExistException();
        }
        bookRepository.delete(book);
    }

    public boolean isBookExist(final String title) {
        return bookRepository.existsByTitle(title);
    }

    public Long countBooks() {
        return bookRepository.count();
    }
}