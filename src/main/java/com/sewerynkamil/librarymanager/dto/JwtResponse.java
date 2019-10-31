package com.sewerynkamil.librarymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@Getter
public class JwtResponse {
    private final String jwttoken;
}