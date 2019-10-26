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
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecimenServiceTestSuite {

    @Autowired
    private SpecimenService specimenService;

    @Autowired
    private BookService bookService;

    @Test
    @Transactional
    public void testFindAllSpecimenForOneBookByBookId() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen);

        // When
        List<Specimen> specimens = specimenService.findAllSpecimensForOneBookByBookId(book.getId());

        // Then
        Assert.assertEquals(2, specimens.size());
    }

    @Test
    @Transactional
    public void testFindAllSpecimenForOneBookByStatusAndBookId() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen = new Specimen(Status.RENTED, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen);

        // When
        List<Specimen> specimens = specimenService.findAllSpecimensForOneBookByStatusAndBookId(Status.RENTED, book.getId());

        // Then
        Assert.assertEquals(1, specimens.size());
    }

    @Test
    @Transactional
    public void testSaveNewSpecimenAndFindOneSpecimen() throws SpecimenNotExistException, BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen = new Specimen(Status.RENTED, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen);

        // When
        Specimen getSpecimen = specimenService.findOneSpecimen(specimen.getId());

        // Then
        Assert.assertEquals(Status.RENTED, getSpecimen.getStatus());
        Assert.assertEquals("Publisher", getSpecimen.getPublisher());
        Assert.assertEquals(new Integer(2008), getSpecimen.getYearOfPublication());
    }

    @Test
    @Transactional
    public void testChangeSpecimenStatusToAvailable() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen = new Specimen(Status.RENTED, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen);

        // When
        Specimen getSpecimen = specimenService.changeSpecimenStatusToAvailable(specimen.getId());

        // Then
        Assert.assertEquals(Status.AVAILABLE, getSpecimen.getStatus());
        Assert.assertEquals("Publisher", getSpecimen.getPublisher());
        Assert.assertEquals(new Integer(2008), getSpecimen.getYearOfPublication());
    }

    @Test
    @Transactional
    public void testChangeSpecimenStatusToRented() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen);

        // When
        Specimen getSpecimen = specimenService.changeSpecimenStatusToRented(specimen.getId());

        // Then
        Assert.assertEquals(Status.RENTED, getSpecimen.getStatus());
        Assert.assertEquals("Publisher", getSpecimen.getPublisher());
        Assert.assertEquals(new Integer(2008), getSpecimen.getYearOfPublication());
    }

    @Test
    @Transactional
    public void testChangeSpecimenStatusToLost() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen);

        // When
        Specimen getSpecimen = specimenService.changeSpecimenStatusToLost(specimen.getId());

        // Then
        Assert.assertEquals(Status.LOST, getSpecimen.getStatus());
        Assert.assertEquals("Publisher", getSpecimen.getPublisher());
        Assert.assertEquals(new Integer(2008), getSpecimen.getYearOfPublication());
    }

    @Test
    @Transactional
    public void testDeleteSpecimen() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen);

        // When
        specimenService.deleteSpecimen(specimen.getId());
        List<Specimen> specimens = specimenService.findAllSpecimensForOneBookByBookId(book.getId());

        // Then
        Assert.assertEquals(1, specimens.size());
    }

    @Test
    @Transactional
    public void testCoubtSpecimensByStatusAndBookId() throws BookExistException {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2008, 9788375748758L);
        bookService.saveNewBook(book, "Publisher", 2008);
        Specimen specimen1 = new Specimen(Status.LOST, "Publisher", 2008, book);
        Specimen specimen2 = new Specimen(Status.LOST, "Publisher", 2008, book);
        Specimen specimen3 = new Specimen(Status.RENTED, "Publisher", 2008, book);
        Specimen specimen4 = new Specimen(Status.RENTED, "Publisher", 2008, book);
        Specimen specimen5 = new Specimen(Status.RENTED, "Publisher", 2008, book);
        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);
        specimenService.saveNewSpecimen(specimen3);
        specimenService.saveNewSpecimen(specimen4);
        specimenService.saveNewSpecimen(specimen5);

        // When
        Long specimensAvailable = specimenService.countSpecimensByStatusAndBookId(Status.AVAILABLE, book.getId());
        Long specimensRented = specimenService.countSpecimensByStatusAndBookId(Status.RENTED, book.getId());
        Long specimensLost = specimenService.countSpecimensByStatusAndBookId(Status.LOST, book.getId());

        // Then
        Assert.assertEquals(new Long(1L), specimensAvailable);
        Assert.assertEquals(new Long(3L), specimensRented);
        Assert.assertEquals(new Long(2L), specimensLost);
    }
}