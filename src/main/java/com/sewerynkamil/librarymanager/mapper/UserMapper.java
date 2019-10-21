package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Kamil Seweryn
 */

@Component
public class UserMapper {
    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword());
    }

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getName(),
                userDto.getSurname(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getPassword());
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getPassword()))
                .collect(Collectors.toList());
    }
}