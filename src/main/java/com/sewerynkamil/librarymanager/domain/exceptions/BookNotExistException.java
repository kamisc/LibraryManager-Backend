package com.sewerynkamil.librarymanager.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author Kamil Seweryn
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This book dosen't exist in the database!")
public class BookNotExistException extends Exception {
    public BookNotExistException() {
        super("This book dosen't exist in the database!");
    }
}