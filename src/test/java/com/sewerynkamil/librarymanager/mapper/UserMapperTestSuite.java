package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.dto.UserDto;
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
public class UserMapperTestSuite {
    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void testMapToUserDto() {
        // Given
        User user = new User("Name", "Surname", "name@gmail.com", 123456789, "password", Role.USER.getRole());
        user.setId(1L);

        // When
        UserDto userDto = userMapper.mapToUserDto(user);

        // Then
        Assert.assertEquals("Name", userDto.getName());
        Assert.assertEquals("Surname", userDto.getSurname());
        Assert.assertEquals("User", userDto.getRole());
    }

    @Test
    @Transactional
    public void testMapToUser() {
        // Given
        UserDto userDto = new UserDto(1L, "Name", "Surname", "name@gmail.com", 123456789, "password", Role.USER.getRole());

        // When
        User user = userMapper.mapToUser(userDto);

        // Then
        Assert.assertEquals("Name", user.getName());
        Assert.assertEquals("Surname", user.getSurname());
        Assert.assertEquals("User", user.getRole());
    }

    @Test
    @Transactional
    public void testMapToUserDtoList() {
        // Given
        List<User> userList = new ArrayList<>();
        User user1 = new User("Name1", "Surname1", "name1@gmail.com", 123456789, "password", Role.USER.getRole());
        User user2 = new User("Name2", "Surname2", "name2@gmail.com", 987654321, "drowssap", Role.USER.getRole());
        user1.setId(1L);
        user2.setId(2L);
        userList.add(user1);
        userList.add(user2);

        // When
        List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);

        // Then
        Assert.assertEquals(2, userDtoList.size());
    }
}