package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.dto.BookDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class BookMapperTestSuite {
    @Autowired
    private BookMapper bookMapper;

    @Test
    @Transactional
    public void testMapToBookDto() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        book.setId(1L);

        // When
        BookDto bookDto = bookMapper.mapToBookDto(book);

        // Then
        Assert.assertEquals("Author", bookDto.getAuthor());
        Assert.assertEquals("Title", bookDto.getTitle());
        Assert.assertEquals("Fantasy", bookDto.getCategory());
    }

    @Test
    @Transactional
    public void testMapToBook() {
        // Given
        BookDto bookDto = new BookDto(1L, "Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);

        // When
        Book book = bookMapper.mapToBook(bookDto);

        // Then
        Assert.assertEquals("Author", bookDto.getAuthor());
        Assert.assertEquals("Title", bookDto.getTitle());
        Assert.assertEquals("Fantasy", bookDto.getCategory());
    }

    @Test
    @Transactional
    public void testMapToBookDtoList() {
        // Given
        List<Book> bookList = new ArrayList<>();
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2008);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 2009);
        book1.setId(1L);
        book2.setId(2L);
        bookList.add(book1);
        bookList.add(book2);

        // When
        List<BookDto> bookDtoList = bookMapper.mapToBookDtoList(bookList);

        // Then
        Assert.assertEquals(2, bookDtoList.size());
    }
}
