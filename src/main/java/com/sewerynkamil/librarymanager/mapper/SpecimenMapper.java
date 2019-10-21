package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.dto.SpecimenDto;
import com.sewerynkamil.librarymanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Kamil Seweryn
 */

@Component
public class SpecimenMapper {
    @Autowired
    private BookRepository bookRepository;

    public SpecimenDto mapToSpecimenDto(final Specimen specimen) {
        return new SpecimenDto(
                specimen.getStatus(),
                specimen.getPublisher(),
                specimen.getYearOfPublication(),
                specimen.getBook().getTitle());
    }

    public Specimen mapToSpecimen(final SpecimenDto specimenDto) {
        return new Specimen(
                specimenDto.getStatus(),
                specimenDto.getPublisher(),
                specimenDto.getYearOfPublication(),
                bookRepository.findByTitle(specimenDto.getBookTitle()));
    }

    public List<SpecimenDto> mapToSpecimenDtoList(final List<Specimen> specimenList) {
        return specimenList.stream()
                .map(specimen -> new SpecimenDto(
                        specimen.getStatus(),
                        specimen.getPublisher(),
                        specimen.getYearOfPublication(),
                        specimen.getBook().getTitle()))
                .collect(Collectors.toList());
    }
}