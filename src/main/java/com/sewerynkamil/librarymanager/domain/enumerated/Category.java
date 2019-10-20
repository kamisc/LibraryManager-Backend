package com.sewerynkamil.librarymanager.domain.enumerated;

/**
 * Author Kamil Seweryn
 */

public enum Category {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    AUTOBIOGRAPHY("Autobiography"),
    BIOGRAPHY("Biography"),
    DIARY("Diary"),
    DRAMA("Drama"),
    CLASSIC("Classic"),
    COMIC("Comic"),
    CRIME("Crime"),
    DETECTIVE("Detective"),
    FABLE("Fable"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical"),
    HUMOR("Humor"),
    HORROR("Horror"),
    POETRY("Poetry"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science fiction"),
    THRILLER("Thriller"),
    TRAGEDY("Tragedy");

    String category;

    Category(String category) {
        this.category = category;
    }

    public static String getCategory(String category) {
        for (Category c : Category.values()) {
            if (c.category == category) {
                return category;
            }
        }
        return null;
    }
}