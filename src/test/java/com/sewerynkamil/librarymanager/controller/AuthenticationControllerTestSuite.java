package com.sewerynkamil.librarymanager.controller;

import com.google.gson.Gson;
import com.sewerynkamil.librarymanager.config.security.AuthenticationEntryPointJwt;
import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.dto.RequestJwtDto;
import com.sewerynkamil.librarymanager.dto.ResponseJwtDto;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@MockBeans({
        @MockBean(AuthenticationEntryPointJwt.class)
})
public class AuthenticationControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private TokenUtilJwt tokenUtilJwt;

    @Test
    public void testCreateAuthenticationToken() throws Exception {
        // Given
        UserDetails user = new User("User", "Pass", new ArrayList<>());
        RequestJwtDto request = new RequestJwtDto("User", "Pass");
        ResponseJwtDto responseJwtDto = new ResponseJwtDto("byleco123");

        when(userService.loadUserByUsername(request.getUsername())).thenReturn(user);
        when(tokenUtilJwt.generateToken(user)).thenReturn(responseJwtDto.getJwttoken());

        // When & Then
        mockMvc.perform(post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveNewUser() throws Exception {
        // Given
        com.sewerynkamil.librarymanager.domain.User user = new com.sewerynkamil.librarymanager.domain.
                User("John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);
        user.setId(1L);

        UserDto userDto = new UserDto("000000001", "John", "Doe", "john@doe.com", 123456789, "482acv58", Role.USER);

        when(userService.saveUser(any(com.sewerynkamil.librarymanager.domain.User.class))).thenReturn(user);
        when(userMapper.mapToUser(any(UserDto.class))).thenReturn(user);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(user);

        // When & Then
        mockMvc.perform(post("/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }


}
