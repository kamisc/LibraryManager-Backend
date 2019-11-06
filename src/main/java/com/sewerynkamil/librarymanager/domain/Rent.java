package com.sewerynkamil.librarymanager.domain;

import com.sewerynkamil.librarymanager.observer.Observable;
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
public class Rent implements Observable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(unique = true)
    private Long id;

    private LocalDate rentDate;

    private LocalDate returnDate;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "specimen_id")
    private Specimen specimen;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Rent(Specimen specimen, User user) {
        this.rentDate = LocalDate.now();
        this.returnDate = rentDate.plusDays(30);
        this.specimen = specimen;
        this.user = user;
    }

    @Override
    public void notifyObserver() {
        user.update(this);
    }
}