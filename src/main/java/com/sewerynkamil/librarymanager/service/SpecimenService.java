package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Specimen;
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

    @Autowired
    public SpecimenService(SpecimenRepository specimenRepository, BookRepository bookRepository) {
        this.specimenRepository = specimenRepository;
    }

    public List<Specimen> findAllSpecimensForOneBookByBookId(final Long bookId) {
        return specimenRepository.findAllByBookId(bookId);
    }

    public List<Specimen> findAllSpecimensForOneBookByStatusAndBookId(final String status, final Long bookId) {
        return specimenRepository.findAllByStatusAndBookId(status, bookId);
    }

    public Specimen findOneSpecimen(final Long id) throws SpecimenNotExistException {
        return specimenRepository.findById(id).orElseThrow(SpecimenNotExistException::new);
    }

    public Specimen saveNewSpecimen(Specimen specimen) {
        return specimenRepository.save(specimen);
    }

    public Specimen updateSpecimen(Specimen specimen) {
        return specimenRepository.save(specimen);
    }

    public void deleteSpecimen(final Specimen specimen) {
        specimen.getBook().getSpecimenList().remove(specimen);
        specimenRepository.delete(specimen);
    }
}