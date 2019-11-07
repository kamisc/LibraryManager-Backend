package com.sewerynkamil.librarymanager.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author Kamil Seweryn
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user exist in the database!")
public class UserExistException extends Exception {
    public UserExistException() {
        super("This user exist in the database!");
    }
}