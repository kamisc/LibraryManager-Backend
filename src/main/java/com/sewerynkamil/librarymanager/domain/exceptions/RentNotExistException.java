package com.sewerynkamil.librarymanager.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author Kamil Seweryn
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This rent dosen't exist in the database!")
public class RentNotExistException extends Exception {
    public RentNotExistException() {
        super("This rent dosen't exist in the database!");
    }
}