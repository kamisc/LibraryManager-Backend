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
@Table(name = "SPECIMENS")
public class Specimen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private String status;

    @NotNull
    @Length(min = 2)
    private String publisher;

    @NotNull
    private Integer yearOfPublication;

    @NotNull
    private Long isbn;

    @NotNull
    private LocalDate creationDate = LocalDate.now();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(targetEntity = Rent.class,
               mappedBy = "specimen",
               cascade = CascadeType.MERGE,
               fetch = FetchType.LAZY)
    private List<Rent> rentList = new ArrayList<>();

    public Specimen(String status, String publisher, Integer yearOfPublication, Book book, Long isbn) {
        this.status = status;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.book = book;
        this.isbn = isbn;
    }
}