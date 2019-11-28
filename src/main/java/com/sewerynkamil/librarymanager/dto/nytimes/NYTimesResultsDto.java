package com.sewerynkamil.librarymanager.dto.nytimes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Author Kamil Seweryn
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NYTimesResultsDto {
    private String section;
    private String title;
    private String url;
    private String byline;
}