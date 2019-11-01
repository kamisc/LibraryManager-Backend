package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.repository.BookRepository;
import com.sewerynkamil.librarymanager.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@Service
public class SpecimenService {
    private SpecimenRepository specimenRepository;
    private BookRepository bookRepository;

    @Autowired
    public SpecimenService(SpecimenRepository specimenRepository, BookRepository bookRepository) {
        this.specimenRepository = specimenRepository;
        this.bookRepository = bookRepository;
    }

    public List<Specimen> findAllSpecimensForOneBookByBookId(final Long bookId) {
        return specimenRepository.findAllByBookId(bookId);
    }

    public List<Specimen> findAllSpecimensForOneBookByStatusAndBookId(final Status status, final Long bookId) {
        return specimenRepository.findAllByStatusAndBookId(status, bookId);
    }

    public Specimen findOneSpecimen(final Long id) throws SpecimenNotExistException {
        return specimenRepository.findById(id).orElseThrow(SpecimenNotExistException::new);
    }

    public Specimen saveNewSpecimen(Specimen specimen) {
        return specimenRepository.save(specimen);
    }

    public Specimen changeSpecimenStatusToAvailable(final Long specimenId) {
        Specimen specimen = specimenRepository.findById(specimenId).get();
        specimen.setStatus(Status.AVAILABLE);
        return specimenRepository.save(specimen);
    }

    public Specimen changeSpecimenStatusToRented(final Long specimenId) {
        Specimen specimen = specimenRepository.findById(specimenId).get();
        specimen.setStatus(Status.RENTED);
        return specimenRepository.save(specimen);
    }

    public Specimen changeSpecimenStatusToLost(final Long specimenId) {
        Specimen specimen = specimenRepository.findById(specimenId).get();
        specimen.setStatus(Status.LOST);
        return specimenRepository.save(specimen);
    }

    public void deleteSpecimen(final Long specimenId) {
        specimenRepository.deleteById(specimenId);
    }

    public Long countSpecimensByStatusAndBookId(final Status status, final Long bookId) {
        return specimenRepository.countByStatusAndBookId(status, bookId);
    }
}