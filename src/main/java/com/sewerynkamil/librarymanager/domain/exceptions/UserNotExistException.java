package com.sewerynkamil.librarymanager.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author Kamil Seweryn
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This user dosen't exist in the database!")
public class UserNotExistException extends Exception {
    public UserNotExistException() {
        super("This user dosen't exist in the database!");
    }
}