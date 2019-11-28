package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.client.NYTimesClient;
import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesTopStoriesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author Kamil Seweryn
 */

@Service
public class NYTimesService {
    private NYTimesClient nyTimesClient;

    @Autowired
    public NYTimesService(NYTimesClient nyTimesClient) {
        this.nyTimesClient = nyTimesClient;
    }

    public NYTimesTopStoriesDto fetchNYTimesTopStories(String section) {
        return nyTimesClient.getNYTimesTopStories(section);
    }
}