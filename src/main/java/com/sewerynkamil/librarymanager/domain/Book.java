package com.sewerynkamil.librarymanager.domain;

import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    @Length(min = 3)
    private String author;

    @NotNull
    @Length(min = 2)
    private String title;

    @NotNull
    private String category;

    @NotNull
    private Integer yearOfFirstPublication;

    @NotNull
    private Long isbn;

    @NotNull
    private LocalDate creationDate = LocalDate.now();

    @OneToMany(targetEntity = Specimen.class,
               mappedBy = "book",
               cascade = CascadeType.ALL,
               fetch = FetchType.EAGER)
    private List<Specimen> specimenList = new ArrayList<>();

    public Book(String author, String title, String category, Integer yearOfFirstPublication, Long isbn) {
        this.author = author;
        this.title = title;
        this.category = category;
        this.yearOfFirstPublication = yearOfFirstPublication;
        this.isbn = isbn;
    }
}