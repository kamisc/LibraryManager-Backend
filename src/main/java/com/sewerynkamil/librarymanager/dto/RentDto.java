package com.sewerynkamil.librarymanager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Author Kamil Seweryn
 */

@NoArgsConstructor
@Getter
@Setter
public class RentDto {
    private String rentId;
    private String userId;
    private String specimenId;
    private String bookTitle;
    private String userEmail;
    private LocalDate rentDate;
    private LocalDate returnDate;

    public RentDto(Long rentId, Long userId, Long specimenId,
                   String bookTitle, String userEmail, LocalDate rentDate, LocalDate returnDate) {
        this.rentId = DtoUtils.generateId(rentId);
        this.userId = DtoUtils.generateId(userId);
        this.specimenId = DtoUtils.generateId(specimenId);
        this.bookTitle = bookTitle;
        this.userEmail = userEmail;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }
}