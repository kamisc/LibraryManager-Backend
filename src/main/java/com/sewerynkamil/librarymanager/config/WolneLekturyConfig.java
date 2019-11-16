package com.sewerynkamil.librarymanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author Kamil Seweryn
 */

@Getter
@Component
public class WolneLekturyConfig {
    //@Value("${wolnelektury.api.endpoint}")
    @Value("https://wolnelektury.pl/api/")
    private String wolneLekturyEndpoint;
}