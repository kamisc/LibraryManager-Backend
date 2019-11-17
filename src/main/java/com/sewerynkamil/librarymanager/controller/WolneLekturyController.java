package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.dto.BookDto;
import com.sewerynkamil.librarymanager.dto.wolneLektury.WolneLekturyAudiobookDto;
import com.sewerynkamil.librarymanager.service.WolneLekturyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1/audiobooks")
public class WolneLekturyController {
    private WolneLekturyService wolneLekturyService;

    @Autowired
    public WolneLekturyController(WolneLekturyService wolneLekturyService) {
        this.wolneLekturyService = wolneLekturyService;
    }

    @GetMapping
    public List<WolneLekturyAudiobookDto> getAllAudiobooksWithLazyLoading(@RequestParam int offset, @RequestParam int limit) {
        return wolneLekturyService.fetchWolneLekturyBoardsWithLazyLoading(offset, limit);
    }

    @GetMapping("/authors/{author}")
    public List<WolneLekturyAudiobookDto> getAllAudiobooksByAuthorStartsWithIgnoreCase(@PathVariable String author) {
        return wolneLekturyService.fetchAllAudiobooksByAuthorStartsWithIgnoreCase(author);
    }

    @GetMapping("/titles/{title}")
    public List<WolneLekturyAudiobookDto> getAllAudiobooksByTitleStartsWithIgnoreCase(@PathVariable String title) {
        return wolneLekturyService.fetchAllAudiobooksByTitleStartsWithIgnoreCase(title);
    }

    @GetMapping("/count")
    public int countAudiobooks() {
        return wolneLekturyService.countAudiobooks();
    }
}