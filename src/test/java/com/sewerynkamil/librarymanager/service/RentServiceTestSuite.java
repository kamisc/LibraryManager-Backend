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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
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
    public void testFindAllRentsWithLazyLoading() throws UserExistException, SpecimenNotExistException, UserNotExistException, BookExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 1999);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book1, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book2, 1231231231231L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

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
        List<Rent> rents = rentService.findAllRentsWithLazyLoading(0, 100);

        // Then
        Assert.assertEquals(2, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllRentsByBookTitleStartsWithIgnoreCase() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 1999);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book1, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book2, 1231231231231L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

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
        List<Rent> rents = rentService.findAllRentsByBookTitleStartsWithIgnoreCase("Title1");

        // Then
        Assert.assertEquals(1, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllRentsByUserEmailStartsWithIgnoreCase() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 1999);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book1, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book2, 1231231231231L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

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
        List<Rent> rents = rentService.findAllRentsByUserEmailStartsWithIgnoreCase("email@gmail.com");

        // Then
        Assert.assertEquals(2, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllByUserId() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FANTASY), 1999);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book1, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book2, 1231231231231L);
        Specimen specimen3 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book2, 1231231231232L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

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
    public void testRentBook() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);

        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen);

        userService.saveUser(user);

        // When
        Rent rent = rentService.rentBook(specimen.getId(), user.getId());

        // Then
        Assert.assertEquals(LocalDate.now(), rent.getRentDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), rent.getReturnDate());
    }

    @Test
    @Transactional
    public void testProlongationBook() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);

        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen);

        userService.saveUser(user);

        rentService.rentBook(specimen.getId(), user.getId());

        // When
        Rent getRent = rentService.prolongationRent(specimen.getId(), user.getId());

        // Then
        Assert.assertEquals(LocalDate.now().plusDays(60), getRent.getReturnDate());
    }

    @Test
    @Transactional
    public void testReturnBook() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

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
        List<Rent> rents = rentService.findAllRentsWithLazyLoading(0, 100);

        // Then
        Assert.assertEquals(rent1.getReturnDate(), LocalDate.now());
        Assert.assertEquals(1, rents.size());
    }

    @Test
    @Transactional
    public void testRentExistBySpecimenId() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen1);
        book.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);

        userService.saveUser(user);

        rentService.rentBook(specimen1.getId(), user.getId());
        rentService.rentBook(specimen2.getId(), user.getId());

        // When
        boolean isExist = rentService.isRentExistBySpecimenId(specimen1.getId());

        // Then
        Assert.assertTrue(isExist);
    }

    @Test
    @Transactional
    public void testRentExistBySpecimenBookTitle() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen1);
        book.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);

        userService.saveUser(user);

        rentService.rentBook(specimen1.getId(), user.getId());
        rentService.rentBook(specimen2.getId(), user.getId());

        // When
        boolean isExist = rentService.isRentExistBySpecimenBookTitle("Title1");

        // Then
        Assert.assertTrue(isExist);
    }

    @Test
    @Transactional
    public void testCountRents() throws BookExistException, UserExistException, SpecimenNotExistException, UserNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011);

        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2001, book, 9788375748758L);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen1);
        book.getSpecimenList().add(specimen2);
        bookService.saveNewBook(book);

        specimenService.saveNewSpecimen(specimen1);
        specimenService.saveNewSpecimen(specimen2);

        userService.saveUser(user);

        rentService.rentBook(specimen1.getId(), user.getId());
        rentService.rentBook(specimen2.getId(), user.getId());

        // When
        Long count = rentService.countRents();

        // Then
        Assert.assertEquals(ofNullable(2L).get(), count);
    }
}