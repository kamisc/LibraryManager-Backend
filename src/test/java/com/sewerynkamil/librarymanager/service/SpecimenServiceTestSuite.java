package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.BookExistException;
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

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class SpecimenServiceTestSuite {
    @Autowired
    private SpecimenService specimenService;

    @Autowired
    private BookService bookService;

    @Test
    @Transactional
    public void testFindAllSpecimenForOneBookByBookId() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        bookService.saveNewBook(book);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2008, book, 9788375748758L);
        specimenService.saveNewSpecimen(specimen);

        // When
        List<Specimen> specimens = specimenService.findAllSpecimensForOneBookByBookId(book.getId());

        // Then
        Assert.assertEquals(1, specimens.size());
    }

    @Test
    @Transactional
    public void testFindAllSpecimenForOneBookByStatusAndBookId() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        bookService.saveNewBook(book);
        Specimen specimen = new Specimen(Status.RENTED.getStatus(), "Publisher", 2008, book, 9788375748758L);
        specimenService.saveNewSpecimen(specimen);

        // When
        List<Specimen> specimens = specimenService.findAllSpecimensForOneBookByStatusAndBookId(Status.RENTED.getStatus(), book.getId());

        // Then
        Assert.assertEquals(1, specimens.size());
    }

    @Test
    @Transactional
    public void testSaveNewSpecimenAndFindOneSpecimen() throws SpecimenNotExistException, BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        bookService.saveNewBook(book);
        Specimen specimen = new Specimen(Status.RENTED.getStatus(), "Publisher", 2008, book, 9788375748758L);
        specimenService.saveNewSpecimen(specimen);

        // When
        Specimen getSpecimen = specimenService.findOneSpecimen(specimen.getId());

        // Then
        Assert.assertEquals(Status.RENTED.getStatus(), getSpecimen.getStatus());
        Assert.assertEquals("Publisher", getSpecimen.getPublisher());
        Assert.assertEquals(new Integer(2008), getSpecimen.getYearOfPublication());
    }

    @Test
    @Transactional
    public void testUpdateSpecimen() throws BookExistException, SpecimenNotExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        bookService.saveNewBook(book);
        Specimen specimen = new Specimen(Status.RENTED.getStatus(), "Publisher", 2008, book, 9788375748758L);
        specimenService.saveNewSpecimen(specimen);

        // When
        Specimen updatedSpecimen = new Specimen(Status.AVAILABLE.getStatus(), "Pub", 2009, book, 9788375748758L);
        updatedSpecimen.setId(specimen.getId());
        specimenService.updateSpecimen(updatedSpecimen);

        // Then
        Assert.assertEquals(Status.AVAILABLE.getStatus(), specimen.getStatus());
        Assert.assertEquals("Pub", specimen.getPublisher());
        Assert.assertEquals(new Integer(2009), specimen.getYearOfPublication());
    }

    @Test
    @Transactional
    public void testDeleteSpecimen() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        bookService.saveNewBook(book);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2008, book, 9788375748758L);
        specimenService.saveNewSpecimen(specimen);

        // When
        specimenService.deleteSpecimen(specimen);
        List<Specimen> specimens = specimenService.findAllSpecimensForOneBookByBookId(book.getId());

        // Then
        Assert.assertEquals(0, specimens.size());
    }

    @Test(expected = SpecimenNotExistException.class)
    @Transactional
    public void testSpecimenNotExistException() throws SpecimenNotExistException {
        // When
        specimenService.findOneSpecimen(1L);
    }
}