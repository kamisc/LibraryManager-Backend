package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.exceptions.UserExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
import com.sewerynkamil.librarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author Kamil Seweryn
 */

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;

        try {
            user = findOneUserByEmail(username);
        } catch (UserNotExistException e) {
            e.getMessage();
        }

        if(user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAllUsersByEmailStartsWithIgnoreCase(final String email) {
        return userRepository.findByEmailStartsWithIgnoreCase(email);
    }

    public User findOneUserById(final Long id) throws UserNotExistException {
        return userRepository.findById(id).orElseThrow(UserNotExistException::new);
    }

    public User findOneUserByEmail(final String email) throws UserNotExistException {
        User user = userRepository.findByEmail(email);
        if(!isUserExist(user.getEmail())) {
            throw new UserNotExistException();
        }
        return user;
    }

    public User saveUser(final User user) throws UserExistException {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserExistException();
        }
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(final User user) throws UserNotExistException {
        if(!userRepository.existsById(user.getId())) {
            throw new UserNotExistException();
        }
        return userRepository.save(user);
    }

    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    public boolean isUserExist(final String email) {
        return userRepository.existsByEmail(email);
    }

    private Set getAuthority(User user) {
        Set authorities = new HashSet();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
        return authorities;
    }
}