package com.sewerynkamil.librarymanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Author Kamil Seweryn
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String accountId = generateAccountId();

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    @NotNull
    @Length(max = 9)
    private Integer phoneNumber;

    @NotNull
    @Length(min = 8)
    private String password;

    @NotNull
    private LocalDate accountCreationDate = LocalDate.now();

    public User(String name, String surname, String email, Integer phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    private String generateAccountId() {
        return ("000000000" + id).substring(id.toString().length() - 1);
    }
}