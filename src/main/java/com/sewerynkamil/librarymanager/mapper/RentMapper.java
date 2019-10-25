package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.dto.RentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Kamil Seweryn
 */

@Component
public class RentMapper {
    public RentDto mapToRentDto(final Rent rent) {
        return new RentDto(
                rent.getId(),
                rent.getUser().getId(),
                rent.getSpecimen().getId(),
                rent.getSpecimen().getBook().getTitle(),
                rent.getUser().getEmail(),
                rent.getRentDate(),
                rent.getReturnDate());
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rentList) {
        return rentList.stream()
                .map(rent -> new RentDto(
                        rent.getId(),
                        rent.getUser().getId(),
                        rent.getSpecimen().getId(),
                        rent.getSpecimen().getBook().getTitle(),
                        rent.getUser().getEmail(),
                        rent.getRentDate(),
                        rent.getReturnDate()))
                .collect(Collectors.toList());
    }
}