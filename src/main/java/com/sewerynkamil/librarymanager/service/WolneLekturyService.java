package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.client.WolneLekturyClient;
import com.sewerynkamil.librarymanager.dto.wolneLektury.WolneLekturyAudiobookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Kamil Seweryn
 */

@Service
public class WolneLekturyService {
    private WolneLekturyClient wolneLekturyClient;

    @Autowired
    public WolneLekturyService(WolneLekturyClient wolneLekturyClient) {
        this.wolneLekturyClient = wolneLekturyClient;
    }

    public List<WolneLekturyAudiobookDto> fetchWolneLekturyBoardsWithLazyLoading(int offset, int limit) {
        return wolneLekturyClient.getWolneLekturyAudiobooks().stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<WolneLekturyAudiobookDto> fetchAllAudiobooksByAuthorStartsWithIgnoreCase(String author) {
        return wolneLekturyClient.getWolneLekturyAudiobooks().stream()
                .filter(audiobook -> audiobook.getAuthor().toLowerCase().startsWith(author))
                .collect(Collectors.toList());
    }

    public List<WolneLekturyAudiobookDto> fetchAllAudiobooksByTitleStartsWithIgnoreCase(String title) {
        return wolneLekturyClient.getWolneLekturyAudiobooks().stream()
                .filter(audiobook -> audiobook.getTitle().toLowerCase().startsWith(title))
                .collect(Collectors.toList());
    }

    public int countAudiobooks() {
        return wolneLekturyClient.getWolneLekturyAudiobooks().size();
    }
}