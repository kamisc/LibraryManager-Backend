package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.RentNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class RentRepositoryTestSuite {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SpecimenRepository specimenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testFindAll() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 1999, 1231231231231L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book1);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book1.getSpecimenList().add(specimen1);
        book2.getSpecimenList().add(specimen2);
        bookRepository.save(book1);
        bookRepository.save(book2);

        specimenRepository.save(specimen1);
        specimenRepository.save(specimen2);

        userRepository.save(user);

        Rent rent1 = new Rent(specimen1, user);
        Rent rent2 = new Rent(specimen2, user);

        rentRepository.save(rent1);
        rentRepository.save(rent2);

        // When
        List<Rent> rents = rentRepository.findAll();

        // Then
        Assert.assertEquals(2, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllByUserId() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 1999, 1231231231231L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book1);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book1.getSpecimenList().add(specimen1);
        book2.getSpecimenList().add(specimen2);
        bookRepository.save(book1);
        bookRepository.save(book2);

        specimenRepository.save(specimen1);
        specimenRepository.save(specimen2);

        userRepository.save(user);

        Rent rent1 = new Rent(specimen1, user);
        Rent rent2 = new Rent(specimen2, user);

        rentRepository.save(rent1);
        rentRepository.save(rent2);

        // When
        List<Rent> rents = rentRepository.findAllByUserId(user.getId());

        // Then
        Assert.assertEquals(2, rents.size());
    }

    @Test
    @Transactional
    public void testFindAllByReturnDate() {
        // Given
        Book book1 = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);
        Book book2 = new Book("Author2", "Title2", Category.categoryFactory(Category.FABLE), 1999, 1231231231231L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book1);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book2);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book1.getSpecimenList().add(specimen1);
        book2.getSpecimenList().add(specimen2);
        bookRepository.save(book1);
        bookRepository.save(book2);

        specimenRepository.save(specimen1);
        specimenRepository.save(specimen2);

        userRepository.save(user);

        Rent rent1 = new Rent(specimen1, user);
        Rent rent2 = new Rent(specimen2, user);

        rentRepository.save(rent1);
        rentRepository.save(rent2);

        // When
        List<Rent> rents = rentRepository.findAllByReturnDate(LocalDate.now().plusDays(30));

        // Then
        Assert.assertEquals(2, rents.size());
    }

    @Test
    @Transactional
    public void testFindBySpecimenId() {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);

        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen);
        bookRepository.save(book);

        specimenRepository.save(specimen);

        userRepository.save(user);

        Rent rent = new Rent(specimen, user);

        rentRepository.save(rent);

        // When
        Rent getRent = rentRepository.findBySpecimenId(specimen.getId());

        // Then
        Assert.assertEquals(Status.AVAILABLE, getRent.getSpecimen().getStatus());
        Assert.assertEquals("Publisher", getRent.getSpecimen().getPublisher());
    }

    @Test
    @Transactional
    public void testSaveRentAndFindById() throws RentNotExistException {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);

        Specimen specimen = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen);
        bookRepository.save(book);

        specimenRepository.save(specimen);

        userRepository.save(user);

        Rent rent = new Rent(specimen, user);

        rentRepository.save(rent);

        // When
        Rent getRent = rentRepository.findById(rent.getId()).orElseThrow(RentNotExistException::new);

        // Then
        Assert.assertEquals(LocalDate.now(), getRent.getRentDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), getRent.getReturnDate());
    }

    @Test
    @Transactional
    public void testDeleteById() {
        // Given
        Book book = new Book("Author1", "Title1", Category.categoryFactory(Category.FANTASY), 2011, 9788375748758L);

        Specimen specimen1 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);
        Specimen specimen2 = new Specimen(Status.AVAILABLE, "Publisher", 2001, book);

        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());

        book.getSpecimenList().add(specimen1);
        book.getSpecimenList().add(specimen2);
        bookRepository.save(book);

        specimenRepository.save(specimen1);
        specimenRepository.save(specimen2);

        userRepository.save(user);

        Rent rent1 = new Rent(specimen1, user);
        Rent rent2 = new Rent(specimen2, user);

        rentRepository.save(rent1);
        rentRepository.save(rent2);

        // When
        rentRepository.deleteById(rent1.getId());
        List<Rent> rents = rentRepository.findAll();

        // Then
        Assert.assertEquals(1, rents.size());
    }
}