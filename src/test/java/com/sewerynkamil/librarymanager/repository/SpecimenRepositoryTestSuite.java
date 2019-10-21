package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecimenRepositoryTestSuite {

    @Autowired
    private SpecimenRepository specimenRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testFindAllByBookId() {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen3 = new Specimen(Status.RENTED, "Publisher", 2001, book);

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
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen3 = new Specimen(Status.RENTED, "Publisher", 2001, book);

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
        List<Specimen> available = specimenRepository.findAllByStatusAndBookId(Status.AVAILABLE, book.getId());
        List<Specimen> rented = specimenRepository.findAllByStatusAndBookId(Status.RENTED, book.getId());

        // Then
        Assert.assertEquals(2, available.size());
        Assert.assertEquals(1, rented.size());
    }

    @Test
    @Transactional
    public void testSaveSpecimenAndFindById() throws Exception {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        specimen.setBook(book);
        book.getSpecimenList().add(specimen);

        bookRepository.save(book);
        specimenRepository.save(specimen);

        // When
        Specimen getSpecimen = specimenRepository.findById(specimen.getId()).orElseThrow(Exception::new);
        int specimenListSize = book.getSpecimenList().size();

        // Then
        Assert.assertEquals(Status.AVAILABLE, getSpecimen.getStatus());
        Assert.assertEquals("Publisher", getSpecimen.getPublisher());
        Assert.assertEquals(1, specimenListSize);
    }

    @Test
    @Transactional
    public void testDeleteSpecimen() {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

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

    @Test
    @Transactional
    public void testCountByStatusAndBookId() {
        // Given
        Book book = new Book("Author", "Title", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen3 = new Specimen(Status.LOST, "Publisher", 2001, book);

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
        Long available = specimenRepository.countByStatusAndBookId(Status.AVAILABLE, book.getId());
        Long lost = specimenRepository.countByStatusAndBookId(Status.LOST, book.getId());

        // Then
        Assert.assertEquals(Optional.of(2L).get(), available);
        Assert.assertEquals(Optional.of(1L).get(), lost);
    }
}