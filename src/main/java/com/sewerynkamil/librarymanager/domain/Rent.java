package com.sewerynkamil.librarymanager.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Author Kamil Seweryn
 */

@Data
@Entity
@Table(name = "RENTS")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(unique = true)
    private Long id;

    private LocalDate rentDate;

    private LocalDate returnDate;

    public Rent() {
        this.rentDate = LocalDate.now();
        this.returnDate = rentDate.plusDays(30);
    }
}