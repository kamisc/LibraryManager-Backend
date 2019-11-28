package com.sewerynkamil.librarymanager.dto.nytimes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NYTimesTopStoriesDto {
    private List<NYTimesResultsDto> results;
}