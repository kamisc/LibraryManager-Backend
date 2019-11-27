package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.dto.SpecimenDto;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class SpecimenMapperTestSuite {
    @Autowired
    private SpecimenMapper specimenMapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testMapToSpecimenDto() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        book.setId(1L);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2005, book, 1234567891111L);
        specimen.setId(2L);

        // When
        SpecimenDto specimenDto = specimenMapper.mapToSpecimenDto(specimen);

        // Then
        Assert.assertEquals("Available", specimenDto.getStatus());
        Assert.assertEquals("Publisher", specimenDto.getPublisher());
        Assert.assertEquals("Title", specimenDto.getBookTitle());
    }

    @Test
    @Transactional
    public void testMapToSpecimen() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        book.setId(1L);
        bookRepository.save(book);
        SpecimenDto specimenDto = new SpecimenDto(2L, Status.UNAVAILABLE.getStatus(), "Publisher", 2005, "Title", 1234567891111L);

        // When
        Specimen specimen = specimenMapper.mapToSpecimen(specimenDto);

        // Then
        Assert.assertEquals("Unavailable", specimen.getStatus());
        Assert.assertEquals("Publisher", specimen.getPublisher());
        Assert.assertEquals("Title", specimen.getBook().getTitle());
    }

    @Test
    @Transactional
    public void testMapToSpecimenDtoList() {
        // Given
        List<Specimen> specimenList = new ArrayList<>();
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        book.setId(1L);
        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher1", 2005, book, 1234567891111L);
        Specimen specimen2 = new Specimen(Status.UNAVAILABLE.getStatus(), "Publisher2", 2008, book, 1234567891111L);
        specimen1.setId(2L);
        specimen2.setId(3L);
        specimenList.add(specimen1);
        specimenList.add(specimen2);

        // When
        List<SpecimenDto> specimenDtoList = specimenMapper.mapToSpecimenDtoList(specimenList);

        // Then
        Assert.assertEquals(2, specimenDtoList.size());
    }
}