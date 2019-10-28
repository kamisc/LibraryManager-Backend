package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.RentNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
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
    private SpecimenRepository specimenRepository;
    private UserRepository userRepository;

    @Autowired
    public RentService(RentRepository rentRepository, SpecimenRepository specimenRepository, UserRepository userRepository) {
        this.rentRepository = rentRepository;
        this.specimenRepository = specimenRepository;
        this.userRepository = userRepository;
    }

    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }

    public List<Rent> findAllRentsByUserId(final Long userId) {
        return rentRepository.findAllByUserId(userId);
    }

    public Rent findOneRentBySpecimenId(final Long specimenId) {
        return rentRepository.findBySpecimenId(specimenId);
    }

    public Rent findOneRentById(final Long rentId) throws RentNotExistException {
        return rentRepository.findById(rentId).orElseThrow(RentNotExistException::new);
    }

    public Rent rentBook(final Long specimenId, final Long userId) throws SpecimenNotExistException, UserNotExistException {
        Specimen specimen = specimenRepository.findById(specimenId).orElseThrow(SpecimenNotExistException::new);
        specimen.setStatus(Status.RENTED);
        specimenRepository.save(specimen);

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        Rent rent = new Rent(specimen, user);

        return rentRepository.save(rent);
    }

    public Rent prolongationRent(final Long specimenId, final Long userId) {
        Rent rent = rentRepository.findBySpecimenIdAndUserId(specimenId, userId);
        rent.setReturnDate(rent.getReturnDate().plusDays(30));
        return rentRepository.save(rent);
    }

    public void returnBook(final Long specimenId, final Long userId) {
        Rent rent = rentRepository.findBySpecimenIdAndUserId(specimenId, userId);
        rent.getSpecimen().setStatus(Status.AVAILABLE);
        rent.setReturnDate(LocalDate.now());
        rentRepository.deleteById(rent.getId());
    }
}