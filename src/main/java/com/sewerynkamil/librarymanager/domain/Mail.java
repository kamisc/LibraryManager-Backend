package com.sewerynkamil.librarymanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author Kamil Seweryn
 */

@Getter
@AllArgsConstructor
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
}