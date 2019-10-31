package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Author Kamil Seweryn
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findOneUserByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());        }
    }
}