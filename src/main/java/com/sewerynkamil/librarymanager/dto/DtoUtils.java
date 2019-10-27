package com.sewerynkamil.librarymanager.dto;

/**
 * Author Kamil Seweryn
 */

public class DtoUtils {
    protected static String generateId(Long id) {
        return ("000000000" + id).substring(id.toString().length());
    }
}
