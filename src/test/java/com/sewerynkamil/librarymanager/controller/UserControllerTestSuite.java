package com.sewerynkamil.librarymanager.controller;

import com.google.gson.Gson;
import com.sewerynkamil.librarymanager.config.security.AuthenticationEntryPointJwt;
import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.dto.UserDto;
import com.sewerynkamil.librarymanager.mapper.UserMapper;
import com.sewerynkamil.librarymanager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@MockBeans({
        @MockBean(TokenUtilJwt.class),
        @MockBean(AuthenticationEntryPointJwt.class)
})
public class UserControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUsers() throws Exception {
        // Given
        List<User> userList = new ArrayList<>();
        when(userService.findAllUsers()).thenReturn(userList);

        // When & Then
        mockMvc.perform(get("/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUsersByEmailStartsWithIgnoreCase() throws Exception {
        // Given
        List<User> userList = new ArrayList<>();
        String test = "use";
        when(userService.findAllUsersByEmailStartsWithIgnoreCase(anyString())).thenReturn(userList);

        // When & Then
        mockMvc.perform(get("/v1/users/" + test)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void testGetOneUserById() throws Exception {
        // Given
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);
        user.setId(1L);

        UserDto userDto = new UserDto("000000001", "John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);

        when(userService.findOneUserById(user.getId())).thenReturn(user);
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDto);

        // When & Then
        mockMvc.perform(get("/v1/users/id/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userAccountId", is("000000001")))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.phoneNumber").value(123456789));
    }

    @Test
    @WithMockUser
    public void testGetOneUserByEmail() throws Exception {
        // Given
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);
        user.setId(1L);

        UserDto userDto = new UserDto("000000001", "John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);

        when(userService.findOneUserByEmail(anyString())).thenReturn(user);
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDto);

        // When & Then
        mockMvc.perform(get("/v1/users/email/" + user.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userAccountId", is("000000001")))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.phoneNumber").value(123456789));
    }

    @Test
    @WithMockUser
    public void testIsUserExist() throws Exception {
        // Given
        String email = "user@user.com";
        when(userService.isUserExist(email)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/v1/users/exist/" + email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveNewUser() throws Exception {
        // Given
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);
        user.setId(1L);

        UserDto userDto = new UserDto("000000001", "John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userMapper.mapToUser(any(UserDto.class))).thenReturn(user);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        // When & Then
        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateUser() throws Exception {
        // Given
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);
        user.setId(1L);

        UserDto updatedUser = new UserDto("000000001", "Jan", "Kowalski", "john@doe.com", 123456789, "482acv58", Role.USER);

        when(userService.updateUser(any(User.class))).thenReturn(user);
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(updatedUser);
        when(userMapper.mapToUser(any(UserDto.class))).thenReturn(user);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedUser);

        // When & Then
        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", is("Jan")))
                .andExpect(jsonPath("$.surname", is("Kowalski")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        // Given
        User user = new User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);
        user.setId(1L);

        when(userService.saveUser(user)).thenReturn(user);

        // When & Then
        mockMvc.perform(delete("/v1/users")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}