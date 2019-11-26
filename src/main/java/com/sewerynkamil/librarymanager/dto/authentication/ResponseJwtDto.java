package com.sewerynkamil.librarymanager.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@Getter
public class ResponseJwtDto {
    private final String jwttoken;

    @Override
    public String toString() {
        return jwttoken;
    }
}