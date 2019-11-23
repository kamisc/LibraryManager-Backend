package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.RentNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
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
    private EmailService emailService;

    @Autowired
    public RentService(
            RentRepository rentRepository,
            SpecimenRepository specimenRepository,
            UserRepository userRepository,
            EmailService emailService) {
        this.rentRepository = rentRepository;
        this.specimenRepository = specimenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
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

    public Rent findOneRentById(final Long rentId) throws RentNotExistException {
        return rentRepository.findById(rentId).orElseThrow(RentNotExistException::new);
    }

    public Rent rentBook(final Long specimenId, final Long userId) throws SpecimenNotExistException, UserNotExistException {
        Specimen specimen = specimenRepository.findById(specimenId).orElseThrow(SpecimenNotExistException::new);
        specimen.setStatus(Status.RENTED.getStatus());
        specimenRepository.save(specimen);

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        Rent rent = new Rent(specimen, user);
        emailService.send(user.update(rent));
        return rentRepository.save(rent);
    }

    public Rent prolongationRent(final Long specimenId, final Long userId) {
        Rent rent = rentRepository.findBySpecimenIdAndUserId(specimenId, userId);
        rent.setReturnDate(rent.getReturnDate().plusDays(30));
        return rentRepository.save(rent);
    }

    public void returnBook(Long id) {
        Rent rent = rentRepository.findById(id).get();
        rent.getSpecimen().setStatus(Status.AVAILABLE.getStatus());
        rent.setReturnDate(LocalDate.now());
        rent.getSpecimen().getRentList().remove(rent);
        rent.getUser().getRentList().remove(rent);
        rentRepository.deleteById(id);
    }
}