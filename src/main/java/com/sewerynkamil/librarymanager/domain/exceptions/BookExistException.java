package com.sewerynkamil.librarymanager.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author Kamil Seweryn
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This book exist in the database!")
public class BookExistException extends Exception {
    public BookExistException() {
        super("This book exist in the database!");
    }
}