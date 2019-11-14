package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.config.security.TokenUtilJwt;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.exceptions.UserExistException;
import com.sewerynkamil.librarymanager.dto.RequestJwtDto;
import com.sewerynkamil.librarymanager.dto.ResponseJwtDto;
import com.sewerynkamil.librarymanager.dto.UserDto;
import com.sewerynkamil.librarymanager.mapper.UserMapper;
import com.sewerynkamil.librarymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Author Kamil Seweryn
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private TokenUtilJwt tokenUtilJwt;
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            TokenUtilJwt tokenUtilJwt,
            UserService userService,
            UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenUtilJwt = tokenUtilJwt;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJwtDto createAuthenticationToken(@RequestBody RequestJwtDto authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenUtilJwt.generateToken(userDetails);

        return new ResponseJwtDto(token);
    }

    @PostMapping(value = "/register")
    public void registerUser(@RequestBody UserDto userDto) throws UserExistException {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}