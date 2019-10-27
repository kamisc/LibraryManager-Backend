package com.sewerynkamil.librarymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String userAccountId;
    private String name;
    private String surname;
    private String email;
    private Integer phoneNumber;
    private String password;

    public UserDto(Long id, String name, String surname, String email, Integer phoneNumber, String password) {
        this.userAccountId = DtoUtils.generateId(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}