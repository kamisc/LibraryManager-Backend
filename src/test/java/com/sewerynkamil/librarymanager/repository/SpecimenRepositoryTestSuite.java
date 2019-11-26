package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
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
import java.util.Optional;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class SpecimenRepositoryTestSuite {
    @Autowired
    private SpecimenRepository specimenRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testFindAllByBookId() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748759L);
        Specimen specimen3 = new Specimen(Status.RENTED.getStatus(), "Publisher", 2001, book, 9788375748718L);

        specimen1.setBook(book);
        specimen2.setBook(book);
        specimen3.setBook(book);
        book.getSpecimenList().add(specimen1);
        book.getSpecimenList().add(specimen2);
        book.getSpecimenList().add(specimen3);

        bookRepository.save(book);
        specimenRepository.save(specimen1);
        specimenRepository.save(specimen2);
        specimenRepository.save(specimen3);

        // When
        List<Specimen> specimens = specimenRepository.findAllByBookId(book.getId());

        // Then
        Assert.assertEquals(3, specimens.size());
    }

    @Test
    @Transactional
    public void testFindAllByStatusAndBookId() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748759L);
        Specimen specimen3 = new Specimen(Status.RENTED.getStatus(), "Publisher", 2001, book, 9788375748757L);

        specimen1.setBook(book);
        specimen2.setBook(book);
        specimen3.setBook(book);
        book.getSpecimenList().add(specimen1);
        book.getSpecimenList().add(specimen2);
        book.getSpecimenList().add(specimen3);

        bookRepository.save(book);
        specimenRepository.save(specimen1);
        specimenRepository.save(specimen2);
        specimenRepository.save(specimen3);

        // When
        List<Specimen> available = specimenRepository.findAllByStatusAndBookId(Status.AVAILABLE.getStatus(), book.getId());
        List<Specimen> rented = specimenRepository.findAllByStatusAndBookId(Status.RENTED.getStatus(), book.getId());

        // Then
        Assert.assertEquals(2, available.size());
        Assert.assertEquals(1, rented.size());
    }

    @Test
    @Transactional
    public void testSaveSpecimenAndFindById() throws SpecimenNotExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        specimen.setBook(book);
        book.getSpecimenList().add(specimen);

        bookRepository.save(book);
        specimenRepository.save(specimen);

        // When
        Specimen getSpecimen = specimenRepository.findById(specimen.getId()).orElseThrow(SpecimenNotExistException::new);
        int specimenListSize = book.getSpecimenList().size();

        // Then
        Assert.assertEquals(Status.AVAILABLE.getStatus(), getSpecimen.getStatus());
        Assert.assertEquals("Publisher", getSpecimen.getPublisher());
        Assert.assertEquals(1, specimenListSize);
    }

    @Test
    @Transactional
    public void testDeleteSpecimen() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2011);
        Specimen specimen = new Specimen(Status.UNAVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        specimen.setBook(book);
        book.getSpecimenList().add(specimen);

        bookRepository.save(book);
        specimenRepository.save(specimen);

        // When
        specimenRepository.delete(specimen);
        book.getSpecimenList().clear();
        int specimenListSize = specimenRepository.findAllByBookId(book.getId()).size();

        // Then
        Assert.assertEquals(0, specimenListSize);
    }
}