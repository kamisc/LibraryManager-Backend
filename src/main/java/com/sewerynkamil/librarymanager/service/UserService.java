package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAllUsersByEmailStartsWithIgnoreCase(final String email) {
        return userRepository.findByEmailStartsWithIgnoreCase(email);
    }

    public User findOneUserById(final Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
    }

    public User findOneUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public void deleteUser(final User user) {
        userRepository.delete(user);
    }

    public boolean isUserExist(final String email) {
        return userRepository.existsByEmail(email);
    }
}