package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.exceptions.UserExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
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
}