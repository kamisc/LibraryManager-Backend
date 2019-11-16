package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.client.WolneLekturyClient;
import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.exceptions.BookExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.BookNotExistException;
import com.sewerynkamil.librarymanager.dto.wolneLektury.WolneLekturyAudiobookDto;
import com.sewerynkamil.librarymanager.repository.BookRepository;
import com.sewerynkamil.librarymanager.repository.SpecimenRepository;
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
    private SpecimenRepository specimenRepository;
    private WolneLekturyClient wolneLekturyClient;

    @Autowired
    public BookService(BookRepository bookRepository, SpecimenRepository specimenRepository, WolneLekturyClient wolneLekturyClient) {
        this.bookRepository = bookRepository;
        this.specimenRepository = specimenRepository;
        this.wolneLekturyClient = wolneLekturyClient;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findAllBooksWithLazyLoading(int offset, int limit) {
        return bookRepository.findAll().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Book> findAllBooksByTitleStartsWithIgnoreCase(String title) {
        return bookRepository.findByTitleStartsWithIgnoreCase(title);
    }

    public List<Book> findAllBooksByAuthorStartsWithIgnoreCase(String author) {
        return bookRepository.findByAuthorStartsWithIgnoreCase(author);
    }

    public List<Book> findAllBooksByCategoryStartsWithIgnoreCase(String category) {
        return bookRepository.findByCategoryStartsWithIgnoreCase(category);
    }

    public Book findOneBook(Long id) throws BookNotExistException {
        Book book = bookRepository.findById(id).orElseThrow(BookNotExistException::new);
        if(!bookRepository.existsByTitle(book.getTitle())) {
            throw new BookNotExistException();
        }
        return book;
    }

    public Book saveNewBook(Book book) throws BookExistException {
        if(bookRepository.existsByTitle(book.getTitle())) {
            throw new BookExistException();
        }
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) throws BookNotExistException {
        if(!bookRepository.existsByTitle(book.getTitle())) {
            throw new BookNotExistException();
        }
        return bookRepository.save(book);
    }

    public void deleteBook(Book book) throws BookNotExistException {
        if(!bookRepository.existsByTitle(book.getTitle())) {
            throw new BookNotExistException();
        }
        bookRepository.delete(book);
    }

    public boolean isBookExist(String title) {
        return bookRepository.existsByTitle(title);
    }

    /*public List<WolneLekturyAudiobookDto> fetchWolneLekturyBoards() {
        return wolneLekturyClient.getWolneLekturyAudiobooks();
    }

    public List<WolneLekturyAudiobookDto> fetchWolneLekturyBoardsWithLazyLoading(int offset, int limit) {
        return wolneLekturyClient.getWolneLekturyAudiobooks().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }*/
}