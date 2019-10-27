package com.sewerynkamil.librarymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author Kamil Seweryn
 */

@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String author;
    private String title;
    private String category;
    private Integer yearOfFirstPublication;
    private Long isbn;

    public BookDto(String author, String title, String category, Integer yearOfFirstPublication, Long isbn) {
        this.author = author;
        this.title = title;
        this.category = category;
        this.yearOfFirstPublication = yearOfFirstPublication;
        this.isbn = isbn;
    }
}