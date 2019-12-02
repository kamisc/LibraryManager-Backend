package com.sewerynkamil.librarymanager.controller.nytimes;

import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesTopStoriesDto;
import com.sewerynkamil.librarymanager.service.nytimes.NYTimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author Kamil Seweryn
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1/topstories")
public class NYTimesController {
    private NYTimesService nyTimesService;

    @Autowired
    public NYTimesController(NYTimesService nyTimesService) {
        this.nyTimesService = nyTimesService;
    }

    @GetMapping("/{section}")
    public NYTimesTopStoriesDto getAllTopStoriesBySection(@PathVariable String section) {
        return nyTimesService.fetchNYTimesTopStories(section);
    }
}