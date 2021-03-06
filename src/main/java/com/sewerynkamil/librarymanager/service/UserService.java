package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.domain.exceptions.UserExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserHasRentsException;
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
import java.util.stream.Collectors;

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

    public List<User> findAllUsersWithLazyLoading(final int offset, final int limit) {
        return userRepository.findAll().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<User> findAllUsersByNameStartsWithIgnoreCase(final String name) {
        return userRepository.findByNameStartsWithIgnoreCase(name);
    }

    public List<User> findAllUsersBySurnameStartsWithIgnoreCase(final String surname) {
        return userRepository.findBySurnameStartsWithIgnoreCase(surname);
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
        } else if(user.getEmail().equals("admin@library.com")) {
            user.setRole(Role.ADMIN.getRole());
        }
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(final User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(final User user) throws UserNotExistException, UserHasRentsException {
        if(!userRepository.existsByEmail(user.getEmail())) {
            throw new UserNotExistException();
        } else if (!userRepository.findByEmail(user.getEmail()).getRentList().isEmpty()) {
            throw new UserHasRentsException();
        }
        userRepository.delete(user);
    }

    public boolean isUserHasRents(final String email) {
        return !userRepository.findByEmail(email).getRentList().isEmpty();
    }

    public boolean isUserExist(final String email) {
        return userRepository.existsByEmail(email);
    }

    public Long countUsers() {
        return userRepository.count();
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
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}