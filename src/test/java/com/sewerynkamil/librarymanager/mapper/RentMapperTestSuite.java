package com.sewerynkamil.librarymanager.mapper;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.Rent;
import com.sewerynkamil.librarymanager.domain.Specimen;
import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.dto.RentDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(JavaMailSender.class)
public class RentMapperTestSuite {
    @Autowired
    private RentMapper rentMapper;

    @Test
    @Transactional
    public void testMapToRentDto() {
        // Given
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        book.setId(1L);
        Specimen specimen = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2005, book, 1234567891111L);
        specimen.setId(2L);
        User user = new User("Name", "Surname", "name@gmail.com", 123456789, "password", Role.USER.getRole());
        user.setId(3L);
        Rent rent = new Rent(specimen, user);
        rent.setId(4L);

        // When
        RentDto rentDto = rentMapper.mapToRentDto(rent);

        // Then
        Assert.assertEquals("Title", rentDto.getBookTitle());
        Assert.assertEquals("name@gmail.com", rentDto.getUserEmail());
    }

    @Test
    @Transactional
    public void testMapToRentDtoList() {
        // Given
        List<Rent> rentList = new ArrayList<>();
        Book book = new Book("Author", "Title", Category.categoryFactory(Category.FANTASY), 2008);
        book.setId(1L);
        Specimen specimen1 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2005, book, 1234567891111L);
        specimen1.setId(2L);
        Specimen specimen2 = new Specimen(Status.AVAILABLE.getStatus(), "Publisher", 2005, book, 1234567891111L);
        specimen2.setId(3L);
        User user = new User("Name", "Surname", "name@gmail.com", 123456789, "password", Role.USER.getRole());
        user.setId(4L);
        Rent rent1 = new Rent(specimen1, user);
        rent1.setId(5L);
        Rent rent2 = new Rent(specimen2, user);
        rent2.setId(6L);
        rentList.add(rent1);
        rentList.add(rent2);

        // When
        List<RentDto> rentDtoList = rentMapper.mapToRentDtoList(rentList);

        // Then
        Assert.assertEquals(2, rentDtoList.size());
    }
}