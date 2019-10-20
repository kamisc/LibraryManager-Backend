package com.sewerynkamil.librarymanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Author Kamil Seweryn
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "RENTS")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(unique = true)
    private Long id;

    private LocalDate rentDate;

    private LocalDate returnDate;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specimen_id")
    private Specimen specimen;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Rent(Specimen specimen, User user) {
        this.rentDate = LocalDate.now();
        this.returnDate = rentDate.plusDays(30);
        this.specimen = specimen;
        this.user = user;
    }
}