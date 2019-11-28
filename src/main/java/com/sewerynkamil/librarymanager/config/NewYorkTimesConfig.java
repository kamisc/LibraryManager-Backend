package com.sewerynkamil.librarymanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author Kamil Seweryn
 */

@Getter
@Component
public class NewYorkTimesConfig {
    @Value("${nytimes.api.endpoint}")
    private String nyTimesEndpoint;

    @Value("${nytimes.api.key}")
    private String nyTimesKey;
}