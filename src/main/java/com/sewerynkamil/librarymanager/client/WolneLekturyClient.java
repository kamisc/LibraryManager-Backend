package com.sewerynkamil.librarymanager.client;

import com.sewerynkamil.librarymanager.config.WolneLekturyConfig;
import com.sewerynkamil.librarymanager.dto.wolneLektury.WolneLekturyAudiobookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Author Kamil Seweryn
 */

@Component
public class WolneLekturyClient {
    private WolneLekturyConfig wolneLekturyConfig;
    private RestTemplate restTemplate;

    @Autowired
    public WolneLekturyClient(WolneLekturyConfig wolneLekturyConfig, RestTemplate restTemplate) {
        this.wolneLekturyConfig = wolneLekturyConfig;
        this.restTemplate = restTemplate;
    }

    public List<WolneLekturyAudiobookDto> getWolneLekturyAudiobooks() {
        try {
            WolneLekturyAudiobookDto[] response = restTemplate.getForObject(
                    wolneLekturyConfig.getWolneLekturyEndpoint() + "audiobooks/", WolneLekturyAudiobookDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new WolneLekturyAudiobookDto[0]));
        } catch (RestClientException e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }
}