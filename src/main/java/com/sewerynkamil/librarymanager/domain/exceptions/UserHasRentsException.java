package com.sewerynkamil.librarymanager.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author Kamil Seweryn
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user has rents! You can't delete him.")
public class UserHasRentsException extends Exception {
    public UserHasRentsException() {
        super("This user has rents! You can't delete him.");
    }
}