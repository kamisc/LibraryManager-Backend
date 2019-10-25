package com.sewerynkamil.librarymanager.dto;

import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author Kamil Seweryn
 */

@NoArgsConstructor
@Getter
@Setter
public class SpecimenDto {
    private Status status;
    private String publisher;
    private Integer yearOfPublication;
    private String bookTitle;

    public SpecimenDto(Status status, String publisher, Integer yearOfPublication, String bookTitle) {
        this.status = status;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.bookTitle = bookTitle;
    }
}