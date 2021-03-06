package com.sewerynkamil.librarymanager.dto.nytimes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author Kamil Seweryn
 */

@AllArgsConstructor
@Getter
public enum NYTimesSection {
    ARTS("Arts"),
    AUTOMOBILES("Automobiles"),
    BOOKS("Books"),
    BUSINESS("Business"),
    FASHION("Fashion"),
    FOOD("Food"),
    HEALTH("Health"),
    HOME("Home"),
    INSIDER("Insider"),
    MAGAZINE("Magazine"),
    MOVIES("Movies"),
    NATIONAL("National"),
    NYREGION("NYRegion"),
    OBITUARIES("Obituaries"),
    OPINION("Opinion"),
    POLITICS("Politics"),
    REALESTATE("Real estate"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    SUNDAYREVIEW("Sunday review"),
    TECHNOLOGY("Technology"),
    THEATER("Theater"),
    TMAGAZINE("TMagazine"),
    TRAVEL("Travel"),
    UPSHOT("Upshot"),
    WORLD("World");

    String section;
}