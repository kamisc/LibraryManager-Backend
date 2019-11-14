package com.sewerynkamil.librarymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import lombok.*;

/**
 * Author Kamil Seweryn
 */

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private Integer phoneNumber;
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Role role = Role.USER;

    public UserDto(String name, String surname, String email, Integer phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}