package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.exceptions.BookNotExistException;
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
public class BookRepositoryTestSuite {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testFindAllBooks() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.TRAGEDY), 1999);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> books = bookRepository.findAll();

        // Then
        Assert.assertEquals(2, books.size());
    }

    @Test
    @Transactional
    public void testFindByTitleStartsWithIgnoreCase() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Author2", "Tite2", Category.categoryFactory(Category.TRAGEDY), 1999);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> booksTit = bookRepository.findByTitleStartsWithIgnoreCase("Tit");
        List<Book> booksTitl = bookRepository.findByTitleStartsWithIgnoreCase("Titl");

        // Then
        Assert.assertEquals(2, booksTit.size());
        Assert.assertEquals(1, booksTitl.size());
    }

    @Test
    @Transactional
    public void testFindByAuthorStartsWithIgnoreCase() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Auhor2", "Title2", Category.categoryFactory(Category.TRAGEDY), 1999);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> booksAu = bookRepository.findByAuthorStartsWithIgnoreCase("Au");
        List<Book> booksAut = bookRepository.findByAuthorStartsWithIgnoreCase("Aut");

        // Then
        Assert.assertEquals(2, booksAu.size());
        Assert.assertEquals(1, booksAut.size());
    }

    @Test
    @Transactional
    public void testFindByCategoryStartsWithIgnoreCase() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 1999);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // When
        List<Book> booksFa = bookRepository.findByCategoryStartsWithIgnoreCase("Fa");
        List<Book> booksFab = bookRepository.findByCategoryStartsWithIgnoreCase("Fab");

        // Then
        Assert.assertEquals(2, booksFa.size());
        Assert.assertEquals(1, booksFab.size());
    }

    @Test
    @Transactional
    public void testSaveBookAndFindById() throws BookNotExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        bookRepository.save(book);

        // When
        Book getBook = bookRepository.findById(book.getId()).orElseThrow(BookNotExistException::new);

        // Then
        Assert.assertEquals("Author", getBook.getAuthor());
        Assert.assertEquals("Title", getBook.getTitle());
        Assert.assertEquals("Fantasy", getBook.getCategory());
    }

    @Test
    @Transactional
    public void testSaveBookAndFindByTitle() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        bookRepository.save(book);

        // When
        Book getBook = bookRepository.findByTitle(book.getTitle());

        // Then
        Assert.assertEquals("Author", getBook.getAuthor());
        Assert.assertEquals("Title", getBook.getTitle());
        Assert.assertEquals("Fantasy", getBook.getCategory());
    }

    @Test
    @Transactional
    public void testDeleteBook() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        bookRepository.save(book);

        // When
        bookRepository.delete(book);
        List<Book> books = bookRepository.findAll();

        // Then
        Assert.assertEquals(0, books.size());
    }

    @Test
    @Transactional
    public void testIsExistsByTitleBook() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.DIARY), 2011);
        bookRepository.save(book);

        // When
        boolean isExist = bookRepository.existsByTitle("Title");

        // Then
        Assert.assertTrue(isExist);
    }

    @Test
    @Transactional
    public void testIsNotExistsByTitleBook() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        bookRepository.save(book);

        // When
        boolean isExist = bookRepository.existsByTitle("Tilte");

        // Then
        Assert.assertFalse(isExist);
    }

    @Test
    @Transactional
    public void testCountBooks() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        bookRepository.save(book);

        // When
        Long count = bookRepository.count();

        // Then
        Assert.assertEquals(ofNullable(1L).get(), count);
    }
}