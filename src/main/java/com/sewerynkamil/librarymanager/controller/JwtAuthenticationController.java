package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.config.security.JwtTokenUtil;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.exceptions.UserExistException;
import com.sewerynkamil.librarymanager.dto.JwtRequest;
import com.sewerynkamil.librarymanager.dto.JwtResponse;
import com.sewerynkamil.librarymanager.dto.UserDto;
import com.sewerynkamil.librarymanager.mapper.UserMapper;
import com.sewerynkamil.librarymanager.service.JwtUserDetailsService;
import com.sewerynkamil.librarymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin("")
public class JwtAuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailsService userDetailsService,
                                       UserService userService,
                                       UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @PostMapping(value = "/register")
    public User registerUser(@RequestBody UserDto userDto) throws UserExistException {
        return userService.saveUser(userMapper.mapToUser(userDto));
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
