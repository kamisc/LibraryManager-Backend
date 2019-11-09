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

    public static String categoryFactory(Category category) {
        switch(category) {
            case ACTION:
                return ACTION.category;
            case ADVENTURE:
                return ADVENTURE.category;
            case AUTOBIOGRAPHY:
                return AUTOBIOGRAPHY.category;
            case BIOGRAPHY:
                return BIOGRAPHY.category;
            case DIARY:
                return DIARY.category;
            case DRAMA:
                return DRAMA.category;
            case CLASSIC:
                return CLASSIC.category;
            case COMIC:
                return COMIC.category;
            case CRIME:
                return CRIME.category;
            case DETECTIVE:
                return DETECTIVE.category;
            case FABLE:
                return FABLE.category;
            case FANTASY:
                return FANTASY.category;
            case HISTORICAL:
                return HISTORICAL.category;
            case HUMOR:
                return HUMOR.category;
            case HORROR:
                return HORROR.category;
            case POETRY:
                return POETRY.category;
            case ROMANCE:
                return ROMANCE.category;
            case SCIENCE_FICTION:
                return SCIENCE_FICTION.category;
            case THRILLER:
                return THRILLER.category;
            case TRAGEDY:
                return TRAGEDY.category;
            default:
                return null;
        }
    }

    public static String getCategory(String category) {
        for (Category c : Category.values()) {
            if (c.category.equals(category)) {
                return category;
            }
        }
        return null;
    }
}