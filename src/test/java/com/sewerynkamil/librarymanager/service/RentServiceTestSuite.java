package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentServiceTestSuite {
    @Autowired
    private RentService rentService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private SpecimenService specimenService;

    @Test
    @Transactional
    public void testFindAllRents() throws UserExistException, SpecimenNotExistException, UserNotExistException, BookExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.getCategory("Fable"), 1999, 1231231231231L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book1);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book1.getSpecimenList().add(specimen1);
        book2.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book1);
        bookService.saveNewBook(book2);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);

        userService.saveUser(user);

        rentService.rentBook(specimen1.getId(), user.getId());
        rentService.rentBook(specimen2.getId(), user.getId());

        // When
        List<Rent> rents = rentService.findAllRents();

        // Then
        Assert.assertEquals(2, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllByUserId() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.getCategory("Fable"), 1999, 1231231231231L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book1);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);
        Specimen specimen3 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book1.getSpecimenList().add(specimen1);
        book2.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book1);
        bookService.saveNewBook(book2);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);
        specimenService.saveNewSpecimen(specimen3);

        userService.saveUser(user);

        rentService.rentBook(specimen1.getId(), user.getId());
        rentService.rentBook(specimen2.getId(), user.getId());
        rentService.rentBook(specimen3.getId(), user.getId());

        // When
        List<Rent> rents = rentService.findAllRentsByUserId(user.getId());

        // Then
        Assert.assertEquals(3, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllByReturnDate() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.getCategory("Fable"), 1999, 1231231231231L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book1);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book1.getSpecimenList().add(specimen1);
        book2.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book1);
        bookService.saveNewBook(book2);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);

        userService.saveUser(user);

        rentService.rentBook(specimen1.getId(), user.getId());
        rentService.rentBook(specimen2.getId(), user.getId());

        // When
        List<Rent> rents = rentService.findAllRentsByReturnDate(LocalDate.now().plusDays(30));

        // Then
        Assert.assertEquals(2, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllBySpecimenId() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.getCategory("Fable"), 1999, 1231231231231L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book1);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book1.getSpecimenList().add(specimen1);
        book2.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book1);
        bookService.saveNewBook(book2);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);

        userService.saveUser(user);

        rentService.rentBook(specimen1.getId(), user.getId());
        rentService.rentBook(specimen2.getId(), user.getId());

        // When
        Rent rent = rentService.findOneRentBySpecimenId(specimen1.getId());

        // Then
        Assert.assertEquals(LocalDate.now(), rent.getRentDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), rent.getReturnDate());
        Assert.assertEquals(Status.RENTED, rent.getSpecimen().getStatus());
        Assert.assertEquals("Name", rent.getUser().getName());
    }

    @Test
    @Transactional
    public void testFindOneById() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException, RentNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);

        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book.getSpecimenList().add(specimen);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen);

        userService.saveUser(user);

        Rent rent = rentService.rentBook(specimen.getId(), user.getId());

        // When
        Rent getRent = rentService.findOneRentById(rent.getId());

        // Then
        Assert.assertEquals(Status.RENTED, getRent.getSpecimen().getStatus());
        Assert.assertEquals("Publisher", getRent.getSpecimen().getPublisher());
    }

    @Test
    @Transactional
    public void testRentBookAndFindById() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException, RentNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);

        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book.getSpecimenList().add(specimen);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen);

        userService.saveUser(user);

        Rent rent = rentService.rentBook(specimen.getId(), user.getId());

        // When
        Rent getRent = rentService.findOneRentById(rent.getId());

        // Then
        Assert.assertEquals(LocalDate.now(), getRent.getRentDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), getRent.getReturnDate());
    }

    @Test
    @Transactional
    public void testProlongationBook() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException, RentNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);

        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book.getSpecimenList().add(specimen);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen);

        userService.saveUser(user);

        Rent rent = rentService.rentBook(specimen.getId(), user.getId());

        // When
        Rent getRent = rentService.prolongationRent(specimen.getId(), user.getId());

        // Then
        Assert.assertEquals(LocalDate.now().plusDays(60), getRent.getReturnDate());
    }

    @Test
    @Transactional
    public void testReturnBook() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.getCategory("Fantasy"), 2011, 9788375748758L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER);

        book.getSpecimenList().add(specimen1);
        book.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);

        userService.saveUser(user);

        Rent rent1 = rentService.rentBook(specimen1.getId(), user.getId());
        Rent rent2 = rentService.rentBook(specimen2.getId(), user.getId());

        // When
        rentService.returnBook(rent1.getId());
        List<Rent> rents = rentService.findAllRents();

        // Then
        Assert.assertEquals(rent1.getReturnDate(), LocalDate.now());
    }

    @Test(expected = RentNotExistException.class)
    @Transactional
    public void testRentNotExistException() throws RentNotExistException {
        // When
        rentService.findOneRentById(1L);
    }
}