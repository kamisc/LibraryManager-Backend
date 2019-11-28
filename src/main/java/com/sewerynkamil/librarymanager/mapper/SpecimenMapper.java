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
    private BookRepository bookRepository;

    @Autowired
    public SpecimenMapper(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public SpecimenDto mapToSpecimenDto(final Specimen specimen) {
        SpecimenDto specimenDto = new SpecimenDto(
                specimen.getStatus(),
                specimen.getPublisher(),
                specimen.getYearOfPublication(),
                specimen.getBook().getTitle(),
                specimen.getIsbn());
        specimenDto.setId(specimen.getId());
        return specimenDto;
    }

    public Specimen mapToSpecimen(final SpecimenDto specimenDto) {
        Specimen specimen = new Specimen(
                specimenDto.getStatus(),
                specimenDto.getPublisher(),
                specimenDto.getYearOfPublication(),
                bookRepository.findByTitle(specimenDto.getBookTitle()),
                specimenDto.getIsbn());
        specimen.setId(specimenDto.getId());
        return specimen;
    }

    public List<SpecimenDto> mapToSpecimenDtoList(final List<Specimen> specimenList) {
        return specimenList.stream()
                .map(specimen -> new SpecimenDto(
                        specimen.getId(),
                        specimen.getStatus(),
                        specimen.getPublisher(),
                        specimen.getYearOfPublication(),
                        specimen.getBook().getTitle(),
                        specimen.getIsbn()))
                .collect(Collectors.toList());
    }
}