package com.sewerynkamil.librarymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import lombok.*;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Integer phoneNumber;
    private String password;
    private String role;

    public UserDto(String name, String surname, String email, Integer phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public UserDto(String name, String surname, String email, Integer phoneNumber, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }
}