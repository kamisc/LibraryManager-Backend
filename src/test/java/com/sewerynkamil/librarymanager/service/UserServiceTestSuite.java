package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.exceptions.UserExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
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
public class UserServiceTestSuite {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testFindAllUsers() throws UserExistException {
        // Given
        User user1 = new User("User1", "Surname1", "user1@gmail.com", 123456789, "1a2b3c4d");
        User user2 = new User("User2", "Surname2", "user2@gmail.com", 234567891, "a1b2c3d4");
        userService.saveUser(user1);
        userService.saveUser(user2);

        // When
        List<User> users = userService.findAllUsers();

        // Then
        Assert.assertEquals(2, users.size());
    }

    @Test
    @Transactional
    public void testFindAllUsersByEmailStartsWithIgnoreCase() throws UserExistException {
        // Given
        User user1 = new User("User1", "Surname1", "us1@gmail.com", 123456789, "1a2b3c4d");
        User user2 = new User("User2", "Surname2", "user2@gmail.com", 234567891, "a1b2c3d4");
        userService.saveUser(user1);
        userService.saveUser(user2);

        // When
        List<User> usersUs = userService.findAllUsersByEmailStartsWithIgnoreCase("us");
        List<User> usersUse = userService.findAllUsersByEmailStartsWithIgnoreCase("use");

        // Then
        Assert.assertEquals(2, usersUs.size());
        Assert.assertEquals(1, usersUse.size());
    }

    @Test
    @Transactional
    public void testFindOneUserById() throws UserExistException, UserNotExistException {
        // Given
        User user = new User("User", "Surname", "user@gmail.com", 123456789, "1a2b3c4d");
        userService.saveUser(user);

        // When
        User getUser = userService.findOneUserById(user.getId());

        // Then
        Assert.assertEquals("User", getUser.getName());
        Assert.assertEquals("Surname", getUser.getSurname());
        Assert.assertEquals("user@gmail.com", getUser.getEmail());
    }

    @Test
    @Transactional
    public void testFindOneUserByEmail() throws UserExistException, UserNotExistException {
        // Given
        User user = new User("User", "Surname", "user@gmail.com", 123456789, "1a2b3c4d");
        userService.saveUser(user);

        // When
        User getUser = userService.findOneUserByEmail("user@gmail.com");

        // Then
        Assert.assertEquals("User", getUser.getName());
        Assert.assertEquals("Surname", getUser.getSurname());
        Assert.assertEquals("user@gmail.com", getUser.getEmail());
    }

    @Test
    @Transactional
    public void testSaveUser() throws UserExistException, UserNotExistException {
        // Given
        User user = new User("User", "Surname", "user@gmail.com", 123456789, "1a2b3c4d");

        // When
        User getUser = userService.saveUser(user);

        // Then
        Assert.assertEquals("User", getUser.getName());
        Assert.assertEquals("Surname", getUser.getSurname());
        Assert.assertEquals("user@gmail.com", getUser.getEmail());
    }

    @Test
    @Transactional
    public void testDeleteUser() throws UserExistException, UserNotExistException {
        // Given
        User user = new User("User", "Surname", "user@gmail.com", 123456789, "1a2b3c4d");
        userService.saveUser(user);

        // When
        userService.deleteUser(user);
        List<User> users = userService.findAllUsers();

        // Then
        Assert.assertEquals(0, users.size());
    }

    @Test
    @Transactional
    public void testIsUserExist() throws UserExistException, UserNotExistException {
        // Given
        User user = new User("User", "Surname", "user@gmail.com", 123456789, "1a2b3c4d");
        userService.saveUser(user);

        // When
        boolean isUserExist = userService.isUserExist("user@gmail.com");
        boolean isUserNotExist = userService.isUserExist("use@gmail.com");

        // Then
        Assert.assertTrue(isUserExist);
        Assert.assertFalse(isUserNotExist);
    }

    @Test(expected = UserNotExistException.class)
    @Transactional
    public void testUserNotExistException() throws UserNotExistException {
        // When
        userService.findOneUserById(1L);
    }

    @Test(expected = UserExistException.class)
    @Transactional
    public void testUserExistException() throws UserExistException {
        // Given
        User user = new User("User", "Surname", "user@gmail.com", 123456789, "1a2b3c4d");
        userService.saveUser(user);

        // When
        userService.saveUser(new User("User", "Surname", "user@gmail.com", 123456789, "1a2b3c4d")); }
}