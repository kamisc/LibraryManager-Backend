package com.sewerynkamil.librarymanager.client;

import com.sewerynkamil.librarymanager.config.NewYorkTimesConfig;
import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesTopStoriesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.Optional.ofNullable;

/**
 * Author Kamil Seweryn
 */

@Component
public class NYTimesClient {
    private NewYorkTimesConfig newYorkTimesConfig;
    private RestTemplate restTemplate;

    @Autowired
    public NYTimesClient(NewYorkTimesConfig newYorkTimesConfig, RestTemplate restTemplate) {
        this.newYorkTimesConfig = newYorkTimesConfig;
        this.restTemplate = restTemplate;
    }

    public NYTimesTopStoriesDto getNYTimesTopStories(String section) {
        URI url = UriComponentsBuilder.fromHttpUrl(newYorkTimesConfig.getNyTimesEndpoint() + section + ".json")
                .queryParam("api-key", newYorkTimesConfig.getNyTimesKey())
                .build()
                .encode()
                .toUri();

        try {
            NYTimesTopStoriesDto response = restTemplate.getForObject(url, NYTimesTopStoriesDto.class);
            return ofNullable(response).orElse(new NYTimesTopStoriesDto());
        } catch (RestClientException e) {
            e.getMessage();
            return new NYTimesTopStoriesDto();
        }
    }
}