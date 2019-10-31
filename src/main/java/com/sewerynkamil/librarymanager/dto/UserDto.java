package com.sewerynkamil.librarymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Role role;

    public UserDto(Long id, String name, String surname, String email, Integer phoneNumber, String password) {
        this.userAccountId = DtoUtils.generateId(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}