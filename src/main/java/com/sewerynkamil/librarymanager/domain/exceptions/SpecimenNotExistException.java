package com.sewerynkamil.librarymanager.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author Kamil Seweryn
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This specimen dosen't exist in the database!")
public class SpecimenNotExistException extends Exception {
    public SpecimenNotExistException() {
        super("This specimen exist in the database!");
    }
}