package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
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

import static java.util.Optional.ofNullable;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class UserRepositoryTestSuite {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testFindAllUsers() {
        // Given
        User user1 = new User("Name1", "Surname1", "email1@gmail.com", 123456789, "123456789", Role.USER.getRole());
        User user2 = new User("Name2", "Surname2", "email2@gmail.com", 234567891, "abcdefgh", Role.USER.getRole());
        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> users = userRepository.findAll();

        // Then
        Assert.assertEquals(2, users.size());
    }

    @Test
    @Transactional
    public void testFindByNameStartsWithIgnoreCase() {
        // Given
        User user1 = new User("Nae1", "Surname1", "email1@gmail.com", 123456789, "123456789", Role.USER.getRole());
        User user2 = new User("Nae2", "Surname2", "email2@gmail.com", 234567891, "abcdefgh", Role.USER.getRole());
        User user3 = new User("Name3", "Surname3", "emil@gmail.com", 345678912, "123abc456", Role.USER.getRole());
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // When
        List<User> usersNa = userRepository.findByNameStartsWithIgnoreCase("na");
        List<User> usersNam = userRepository.findByNameStartsWithIgnoreCase("nam");

        // Then
        Assert.assertEquals(3, usersNa.size());
        Assert.assertEquals(1, usersNam.size());
    }

    @Test
    @Transactional
    public void testFindBySurnameStartsWithIgnoreCase() {
        // Given
        User user1 = new User("Name1", "Surame1", "email1@gmail.com", 123456789, "123456789", Role.USER.getRole());
        User user2 = new User("Name2", "Surame2", "email2@gmail.com", 234567891, "abcdefgh", Role.USER.getRole());
        User user3 = new User("Name3", "Surname3", "emil@gmail.com", 345678912, "123abc456", Role.USER.getRole());
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // When
        List<User> usersSur = userRepository.findBySurnameStartsWithIgnoreCase("sur");
        List<User> usersSurn = userRepository.findBySurnameStartsWithIgnoreCase("surn");

        // Then
        Assert.assertEquals(3, usersSur.size());
        Assert.assertEquals(1, usersSurn.size());
    }

    @Test
    @Transactional
    public void testFindByEmailStartsWithIgnoreCase() {
        // Given
        User user1 = new User("Name1", "Surname1", "email1@gmail.com", 123456789, "123456789", Role.USER.getRole());
        User user2 = new User("Name2", "Surname2", "email2@gmail.com", 234567891, "abcdefgh", Role.USER.getRole());
        User user3 = new User("Name3", "Surname3", "emil@gmail.com", 345678912, "123abc456", Role.USER.getRole());
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // When
        List<User> usersEm = userRepository.findByEmailStartsWithIgnoreCase("em");
        List<User> usersEmi = userRepository.findByEmailStartsWithIgnoreCase("emi");

        // Then
        Assert.assertEquals(3, usersEm.size());
        Assert.assertEquals(1, usersEmi.size());
    }

    @Test
    @Transactional
    public void testSaveUserFindById() throws UserNotExistException {
        // Given
        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());
        userRepository.save(user);

        // When
        User getUser = userRepository.findById(user.getId()).orElseThrow(UserNotExistException::new);

        // Then
        Assert.assertEquals("Name", getUser.getName());
        Assert.assertEquals("Surname", getUser.getSurname());
        Assert.assertEquals("email@gmail.com", getUser.getEmail());
    }

    @Test
    @Transactional
    public void testFindByEmail() {
        // Given
        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());
        userRepository.save(user);

        // When
        User getUser = userRepository.findByEmail("email@gmail.com");

        // Then
        Assert.assertEquals("Name", getUser.getName());
        Assert.assertEquals("Surname", getUser.getSurname());
        Assert.assertEquals("email@gmail.com", getUser.getEmail());
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        // Given
        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());
        userRepository.save(user);

        // When
        userRepository.delete(user);
        int size = userRepository.findAll().size();

        // Then
        Assert.assertEquals(0, size);
    }

    @Test
    @Transactional
    public void testExistsByEmail() {
        // Given
        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());
        userRepository.save(user);

        // When
        boolean isExist = userRepository.existsByEmail("email@gmail.com");

        // Then
        Assert.assertTrue(isExist);
    }

    @Test
    @Transactional
    public void testIsNotExistsByEmail() {
        // Given
        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());
        userRepository.save(user);

        // When
        boolean isExist = userRepository.existsByEmail("emial@gmail.com");

        // Then
        Assert.assertFalse(isExist);
    }

    @Test
    @Transactional
    public void testCountBooks() {
        // Given
        User user = new User("Name", "Surname", "email@gmail.com", 123456789, "123456789", Role.USER.getRole());
        userRepository.save(user);

        // When
        Long count = userRepository.count();

        // Then
        Assert.assertEquals(ofNullable(1L).get(), count);
    }
}