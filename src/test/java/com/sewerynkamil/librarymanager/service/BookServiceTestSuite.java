package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.exceptions.BookExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.BookNotExistException;
import com.sewerynkamil.librarymanager.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTestSuite {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testFindAllBooks() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.getCategory("Tragedy"), 1995, 1231231231231L);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> books = bookService.findAllBooks();

        // Then
        Assert.assertEquals(2, books.size());
    }

    @Test
    @Transactional
    public void testFindByTitleStartsWithIgnoreCase() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Tite2", Category.getCategory("Tragedy"), 1999, 1231231231231L);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> booksTit = bookService.findAllBooksByTitleStartsWithIgnoreCase("Tit");
        List<Book> booksTitl = bookService.findAllBooksByTitleStartsWithIgnoreCase("Titl");

        // Then
        Assert.assertEquals(2, booksTit.size());
        Assert.assertEquals(1, booksTitl.size());
    }

    @Test
    @Transactional
    public void testFindByAuthorStartsWithIgnoreCase() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Book book2 = new Book("Auhor2", "Title2", Category.getCategory("Tragedy"), 1999, 1231231231231L);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> booksAu = bookService.findAllBooksByAuthorStartsWithIgnoreCase("Au");
        List<Book> booksAut = bookService.findAllBooksByAuthorStartsWithIgnoreCase("Aut");

        // Then
        Assert.assertEquals(2, booksAu.size());
        Assert.assertEquals(1, booksAut.size());
    }

    @Test
    @Transactional
    public void testFindByCategoryStartsWithIgnoreCase() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Book book2 = new Book("Auhor2", "Title2", Category.getCategory("Tragedy"), 1999, 1231231231231L);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> booksAu = bookService.findAllBooksByCategoryStartsWithIgnoreCase("Au");
        List<Book> booksAut = bookService.findAllBooksByCategoryStartsWithIgnoreCase("Aut");

        // Then
        Assert.assertEquals(2, booksAu.size());
        Assert.assertEquals(1, booksAut.size());
    }

    @Test
    @Transactional
    public void testFindOneBook() throws BookNotExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        bookRepository.save(book);

        // When
        Book getBook = bookService.findOneBook(book.getId());

        // Then
        Assert.assertEquals("Author", getBook.getAuthor());
        Assert.assertEquals("Title", getBook.getTitle());
        Assert.assertEquals("Fantasy", getBook.getCategory());
    }

    @Test
    @Transactional
    public void testSaveNewBook() throws BookExistException, BookNotExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2005);

        // When
        Book getBook = bookService.findOneBook(book.getId());

        // Then
        Assert.assertEquals("Author", getBook.getAuthor());
        Assert.assertEquals("Title", getBook.getTitle());
        Assert.assertEquals("Fantasy", getBook.getCategory());
        Assert.assertEquals("Publisher", getBook.getSpecimenList().get(0).getPublisher());
    }

    @Test
    @Transactional
    public void testUpdateBook() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2005);

        // When
        book = bookService.updateBook(new Book("Autor", "Tytuł", Category.getCategory("Action"), 2011, 9788375748758L));

        // Then
        Assert.assertEquals("Autor", book.getAuthor());
        Assert.assertEquals("Tytuł", book.getTitle());
        Assert.assertEquals("Action", book.getCategory());
    }

    @Test
    @Transactional
    public void testDeleteBook() throws BookNotExistException, BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2005);

        // When
        bookService.deleteBook(book);

        // Then
        Assert.assertTrue(bookRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    public void testIsBookExist() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2005);

        // When
        boolean isBookExist = bookService.isBookExist("Title");

        // Then
        Assert.assertTrue(isBookExist);
    }

    @Test(expected = BookNotExistException.class)
    @Transactional
    public void testBookNotExistException() throws BookNotExistException {
        // When
        bookService.findOneBook(1L);
    }

    @Test(expected = BookExistException.class)
    @Transactional
    public void testBookExistException() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        bookRepository.save(book);

        // When
        bookService.saveNewBook(new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L), "Publisher", 2005);
    }
}