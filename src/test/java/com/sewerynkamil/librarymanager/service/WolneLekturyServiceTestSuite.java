package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.dto.wolneLektury.WolneLekturyAudiobookDto;
import com.sewerynkamil.librarymanager.service.wolnelektury.WolneLekturyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class WolneLekturyServiceTestSuite {
    @Autowired
    private WolneLekturyService wolneLekturyService;

    @Test
    @Transactional
    public void testFetchWolneLekturyBoardsWithLazyLoading() {
        // Given
        int offset = 10;
        int limit = 100;

        // When
        List<WolneLekturyAudiobookDto> audiobooks = wolneLekturyService.fetchWolneLekturyBoardsWithLazyLoading(offset, limit);

        // Then
        Assert.assertFalse(audiobooks.isEmpty());
    }

    @Test
    @Transactional
    public void testFetchAllAudiobooksByAuthorStartsWithIgnoreCase() {
        // Given
        String author = "ignacy";

        // When
        List<WolneLekturyAudiobookDto> audiobooks = wolneLekturyService.fetchAllAudiobooksByAuthorStartsWithIgnoreCase(author);

        // Then
        Assert.assertFalse(audiobooks.isEmpty());
    }

    @Test
    @Transactional
    public void testFetchAllAudiobooksByTitleStartsWithIgnoreCase() {
        // Given
        String title = "dwa";

        // When
        List<WolneLekturyAudiobookDto> audiobooks = wolneLekturyService.fetchAllAudiobooksByTitleStartsWithIgnoreCase(title);

        // Then
        Assert.assertFalse(audiobooks.isEmpty());
    }

    @Test
    @Transactional
    public void testCountAudiobooks() {
        // Given & When
        int count = wolneLekturyService.countAudiobooks();

        // Then
        Assert.assertTrue(count > 0);
    }
}
