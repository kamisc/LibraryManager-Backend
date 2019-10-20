package com.sewerynkamil.librarymanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    @NotNull
    private Integer phoneNumber;

    @NotNull
    @Length(min = 8)
    private String password;

    @NotNull
    private LocalDate accountCreationDate = LocalDate.now();

    @OneToMany(targetEntity = Rent.class,
               mappedBy = "user",
               cascade = CascadeType.ALL,
               fetch = FetchType.EAGER)
    private List<Rent> rentList = new ArrayList<>();

    public User(String name, String surname, String email, Integer phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}