package com.sewerynkamil.librarymanager.scheduler;

import com.sewerynkamil.librarymanager.domain.Mail;
import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.repository.RentRepository;
import com.sewerynkamil.librarymanager.repository.UserRepository;
import com.sewerynkamil.librarymanager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Author Kamil Seweryn
 */

@Component
public class RentedBooksScheduler {
    private static final String SUBJECT = "LibraryManager: Information about return date of your rents";

    @Autowired
    private EmailService emailService;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 22 * * 7")
    public void sendInformationAboutRentedBook() {
        for(User user : userRepository.findAll()) {
            emailService.send(new Mail(
                    user.getEmail(),
                    SUBJECT,
                    "You rented " + user.getRentList().size() + " books from the Library: \n\n" +
                           getUserRents(user)
            ));
        }
    }

    private String getUserRents(User user) {
        String rents = "";
        for(int i = 0; i < user.getRentList().size(); i ++) {
            rents = new StringBuilder(rents).append(
                    user.getRentList().get(i).getSpecimen().getBook().getAuthor() +
                            " - " +
                    user.getRentList().get(i).getSpecimen().getBook().getTitle() +
                            " - ISBN: " +
                    user.getRentList().get(i).getSpecimen().getBook().getIsbn() +
                            " - return date: " +
                    user.getRentList().get(i).getReturnDate() + "\n").toString();
        }
        return rents;
    }
}