package com.sewerynkamil.librarymanager.domain;

import com.sewerynkamil.librarymanager.domain.enumerated.Category;
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
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    private Integer yearOfFirstPublication;

    @NotNull
    private Integer isbn;

    @NotNull
    private LocalDate creationDate = LocalDate.now();

    public Book(String author, String title, Category category, Integer yearOfFirstPublication, Integer isbn) {
        this.author = author;
        this.title = title;
        this.category = category;
        this.yearOfFirstPublication = yearOfFirstPublication;
        this.isbn = isbn;
    }
}