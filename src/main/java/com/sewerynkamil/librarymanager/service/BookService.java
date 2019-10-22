package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.repository.BookRepository;
import com.sewerynkamil.librarymanager.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@Service
public class BookService {
    private BookRepository bookRepository;
    private SpecimenRepository specimenRepository;

    @Autowired
    public BookService(BookRepository bookRepository, SpecimenRepository specimenRepository) {
        this.bookRepository = bookRepository;
        this.specimenRepository = specimenRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findAllBooksByTitleStartsWithIgnoreCase(String title) {
        return bookRepository.findByTitleStartsWithIgnoreCase(title);
    }

    public List<Book> findAllBooksByAuthorStartsWithIgnoreCase(String author) {
        return bookRepository.findByAuthorStartsWithIgnoreCase(author);
    }

    public List<Book> findAllBooksByCategoryStartsWithIgnoreCase(String category) {
        return bookRepository.findByAuthorStartsWithIgnoreCase(category);
    }

    public Book findOneBook(Long id) throws Exception {
        return bookRepository.findById(id).orElseThrow(Exception::new);
    }

    public Book saveNewBook(Book book, String publisher, Integer yearOfPublication) {
        Specimen firstSpecimen = new Specimen(Status.AVAILABLE, publisher, yearOfPublication, book);
        book.getSpecimenList().add(firstSpecimen);
        firstSpecimen.setBook(book);
        specimenRepository.save(firstSpecimen);
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public boolean isBookExist(String title) {
        return bookRepository.existsByTitle(title);
    }
}