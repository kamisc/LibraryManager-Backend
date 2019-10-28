package com.sewerynkamil.librarymanager.domain;

import com.sewerynkamil.librarymanager.domain.enumerated.Status;
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
@Table(name = "SPECIMENS")
public class Specimen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(targetEntity = Rent.class,
               mappedBy = "specimen",
               cascade = CascadeType.MERGE,
               fetch = FetchType.LAZY)
    private List<Rent> rentList = new ArrayList<>();

    public Specimen(Status status, String publisher, Integer yearOfPublication, Book book) {
        this.status = status;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.book = book;
    }
}