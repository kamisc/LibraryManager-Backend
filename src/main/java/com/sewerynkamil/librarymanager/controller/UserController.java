package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.exceptions.UserExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
import com.sewerynkamil.librarymanager.dto.UserDto;
import com.sewerynkamil.librarymanager.mapper.UserMapper;
import com.sewerynkamil.librarymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1/users")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userMapper.mapToUserDtoList(userService.findAllUsers());
    }

    @GetMapping("/email")
    public List<UserDto> getAllUsersByEmailStartsWithIgnoreCase(@RequestParam String email) {
        return userMapper.mapToUserDtoList(userService.findAllUsersByEmailStartsWithIgnoreCase(email));
    }

    @GetMapping("/get")
    public UserDto getOneUserById(@RequestParam Long id) throws UserNotExistException {
        return userMapper.mapToUserDto(userService.findOneUserById(id));
    }

    @GetMapping("/getByEmail")
    public UserDto getOneUserByEmail(@RequestParam String email) throws UserNotExistException {
        return userMapper.mapToUserDto(userService.findOneUserByEmail(email));
    }

    @GetMapping("/exist")
    public boolean isUserExist(@RequestParam String email) {
        return userService.isUserExist(email);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveNewUser(@RequestBody UserDto userDto) throws UserExistException {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUser(@RequestBody UserDto userDto) throws UserNotExistException {
        return userMapper.mapToUserDto(userService.updateUser(userMapper.mapToUser(userDto)));
    }

    @DeleteMapping
    public void deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
    }
}