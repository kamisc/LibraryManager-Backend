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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class BookServiceTestSuite {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testFindAllBooksWithLazyLoading() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2008, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.TRAGEDY), 1995, 1231231231231L);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> books = bookService.findAllBooksWithLazyLoading(0, 100);

        // Then
        Assert.assertEquals(2, books.size());
    }

    @Test
    @Transactional
    public void testFindByTitleStartsWithIgnoreCase() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Tite2", Category.categoryFactory(Category.TRAGEDY), 1999, 1231231231231L);
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
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        Book book2 = new Book("Auhor2", "Title2", Category.categoryFactory(Category.TRAGEDY), 1999, 1231231231231L);
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
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        Book book2 = new Book("Auhor2", "Title2", Category.categoryFactory(Category.FABLE), 1999, 1231231231231L);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> booksFa = bookService.findAllBooksByCategoryStartsWithIgnoreCase("Fa");
        List<Book> booksFan = bookService.findAllBooksByCategoryStartsWithIgnoreCase("Fan");

        // Then
        Assert.assertEquals(2, booksFa.size());
        Assert.assertEquals(1, booksFan.size());
    }

    @Test
    @Transactional
    public void testFindOneBook() throws BookNotExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
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
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        bookService.saveNewBook(book);

        // When
        Book getBook = bookService.findOneBook(book.getId());

        // Then
        Assert.assertEquals("Author", getBook.getAuthor());
        Assert.assertEquals("Title", getBook.getTitle());
        Assert.assertEquals("Fantasy", getBook.getCategory());
    }

    @Test
    @Transactional
    public void testUpdateBook() throws BookExistException, BookNotExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        bookService.saveNewBook(book);

        // When
        Book updatedBook = new Book("Autor", "Title", Category.categoryFactory(Category.ACTION), 2011, 9788375748758L);
        updatedBook.setId(book.getId());
        bookService.updateBook(updatedBook);

        // Then
        Assert.assertEquals("Autor", book.getAuthor());
        Assert.assertEquals("Title", book.getTitle());
        Assert.assertEquals("Action", book.getCategory());
    }

    @Test
    @Transactional
    public void testDeleteBook() throws BookNotExistException, BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        bookService.saveNewBook(book);

        // When
        bookService.deleteBook(book);

        // Then
        Assert.assertTrue(bookRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    public void testIsBookExist() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        bookService.saveNewBook(book);

        // When
        boolean isBookExist = bookService.isBookExist("Title");

        // Then
        Assert.assertTrue(isBookExist);
    }

    @Test
    @Transactional
    public void testCountBooks() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        bookService.saveNewBook(book);

        // When
        Long count = bookService.countBooks();

        // Then
        Assert.assertEquals(ofNullable(1L).get(), count);
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
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        bookRepository.save(book);

        // When
        bookService.saveNewBook(new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L));
    }
}