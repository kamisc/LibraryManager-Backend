package com.sewerynkamil.librarymanager.observer;

import com.sewerynkamil.librarymanager.domain.Mail;
import com.sewerynkamil.librarymanager.domain.Rent;

/**
 * Author Kamil Seweryn
 */

public interface Observer {
    Mail update(Rent rent);
}