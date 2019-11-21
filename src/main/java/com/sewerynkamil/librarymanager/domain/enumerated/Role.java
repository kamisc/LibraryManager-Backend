package com.sewerynkamil.librarymanager.domain.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@Getter
public enum Role {
    USER("User"),
    ADMIN("Admin");

    String role;
}