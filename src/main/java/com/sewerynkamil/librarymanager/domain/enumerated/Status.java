package com.sewerynkamil.librarymanager.domain.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@Getter
public enum Status {
    AVAILABLE("Available"),
    RENTED("Rented"),
    LOST("Lost"),
    UNAVAILABLE("Unavailable");

    String status;
}