package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.repository.BookRepository;
import com.sewerynkamil.librarymanager.repository.RentRepository;
import com.sewerynkamil.librarymanager.repository.SpecimenRepository;
import com.sewerynkamil.librarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@Service
public class RentService {
    private RentRepository rentRepository;
    private BookRepository bookRepository;
    private SpecimenRepository specimenRepository;
    private UserRepository userRepository;

    @Autowired
    public RentService(RentRepository rentRepository, BookRepository bookRepository, SpecimenRepository specimenRepository, UserRepository userRepository) {
        this.rentRepository = rentRepository;
        this.bookRepository = bookRepository;
        this.specimenRepository = specimenRepository;
        this.userRepository = userRepository;
    }

    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }

    public List<Rent> findAllRentsByUserId(final Long userId) {
        return rentRepository.findAllByUserId(userId);
    }

    public List<Rent> findAllRentsByReturnDate(final LocalDate date) {
        return rentRepository.findAllByReturnDate(date);
    }

    public Rent findOneRentBySpecimenId(final Long specimenId) {
        return rentRepository.findBySpecimenId(specimenId);
    }

    public Rent findOneRentById(final Long rentId) throws Exception {
        return rentRepository.findById(rentId).orElseThrow(Exception::new);
    }

    public Rent rentBook(final Long specimenId, final Long userId) throws Exception {
        Specimen specimen = specimenRepository.findById(specimenId).orElseThrow(Exception::new);
        specimen.setStatus(Status.RENTED);
        specimenRepository.save(specimen);

        User user = userRepository.findById(userId).orElseThrow(Exception::new);

        Rent rent = new Rent(specimen, user);

        return rentRepository.save(rent);
    }

    public Rent prolongationRent(final Long specimenId) {
        Rent rent = rentRepository.findBySpecimenId(specimenId);
        rent.setReturnDate(rent.getReturnDate().plusDays(30));
        return rentRepository.save(rent);
    }

    public void returnBook(final Long specimenId) {
        Rent rent = rentRepository.findBySpecimenId(specimenId);
        rent.getSpecimen().setStatus(Status.AVAILABLE);
        rent.setRentDate(null);
        rent.setReturnDate(null);
        rentRepository.deleteById(rent.getId());
    }
}