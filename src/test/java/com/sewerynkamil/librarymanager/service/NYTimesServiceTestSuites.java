package com.sewerynkamil.librarymanager.service;

import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesSection;
import com.sewerynkamil.librarymanager.dto.nytimes.NYTimesTopStoriesDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class NYTimesServiceTestSuites {
    @Autowired
    private NYTimesService nyTimesService;

    @Test
    @Transactional
    public void testFetchNYTimesTopStories() {
        // Given & When
        NYTimesTopStoriesDto stories = nyTimesService.fetchNYTimesTopStories(NYTimesSection.HOME.getSection());

        // Then
        Assert.assertFalse(stories.getResults().isEmpty());
    }
}