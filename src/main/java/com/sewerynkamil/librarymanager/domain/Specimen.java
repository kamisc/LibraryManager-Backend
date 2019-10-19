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
@Table(name = "SPECIMENS")
public class Specimen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Length(min = 2)
    private String publisher;

    @NotNull
    private Integer yearOfPublication;

    @NotNull
    private LocalDate creationDate = LocalDate.now();

    public Specimen(Status status, String publisher, Integer yearOfPublication) {
        this.status = status;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
    }
}