package com.sewerynkamil.librarymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecimenDto {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;
    private String status;
    private String publisher;
    private Integer yearOfPublication;
    private String bookTitle;
    private Long isbn;

    public SpecimenDto(String status, String publisher, Integer yearOfPublication, String bookTitle, Long isbn) {
        this.status = status;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
    }
}