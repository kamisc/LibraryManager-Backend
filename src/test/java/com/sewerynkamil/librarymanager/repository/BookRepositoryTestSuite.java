package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTestSuite {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() throws NotFound {
        // Given
        Book book = new Book("Andrzej Pilipiuk",
                "Wampir z M-3",
                Category.getCategory("Fantasy"),
                2011,
                9788375748758L);
        bookRepository.save(book);

        // When
        Book getBook = bookRepository.findById(book.getId()).orElseThrow(NotFound::new);

        // Then
        Assert.assertEquals("Andrzej Pilipiuk", getBook.getAuthor());
        Assert.assertEquals("Wampir z M-3", getBook.getTitle());
        Assert.assertEquals("Fantasy", getBook.getCategory());
    }

}