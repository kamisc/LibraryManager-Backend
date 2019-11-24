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
public class BookDto {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;
    private String author;
    private String title;
    private String category;
    private Integer yearOfFirstPublication;

    public BookDto(String author, String title, String category, Integer yearOfFirstPublication) {
        this.author = author;
        this.title = title;
        this.category = category;
        this.yearOfFirstPublication = yearOfFirstPublication;
    }
}