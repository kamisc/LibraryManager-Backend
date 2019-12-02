package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
import com.sewerynkamil.librarymanager.dto.RentDto;
import com.sewerynkamil.librarymanager.mapper.RentMapper;
import com.sewerynkamil.librarymanager.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1/rents")
public class RentController {
    private RentService rentService;
    private RentMapper rentMapper;

    @Autowired
    public RentController(RentService rentService, RentMapper rentMapper) {
        this.rentService = rentService;
        this.rentMapper = rentMapper;
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping
    public List<RentDto> getAllRents(@RequestParam int offset, @RequestParam int limit) {
        return rentMapper.mapToRentDtoList(rentService.findAllRentsWithLazyLoading(offset, limit));
    }

    @GetMapping("/user/{userId}")
    public List<RentDto> getAllRentsByUserId(@PathVariable Long userId) {
        return rentMapper.mapToRentDtoList(rentService.findAllRentsByUserId(userId));
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping("/titles/{bookTitle}")
    public List<RentDto> getAllRentsByBookTitleStartsWithIgnoreCase(@PathVariable String bookTitle) {
        return rentMapper.mapToRentDtoList(rentService.findAllRentsByBookTitleStartsWithIgnoreCase(bookTitle));
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping("/emails/{email}")
    public List<RentDto> getAllRentsByUserEmailStartsWithIgnoreCase(@PathVariable String email) {
        return rentMapper.mapToRentDtoList(rentService.findAllRentsByUserEmailStartsWithIgnoreCase(email));
    }

    @GetMapping("/count")
    public Long countRents() {
        return rentService.countRents();
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping("/exist/id/{id}")
    public boolean isRentExistBySpecimenId(@PathVariable Long id) {
        return rentService.isRentExistBySpecimenId(id);
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping("/exist/title/{title}")
    public boolean isRentExistBySpecimenBookTitle(@PathVariable String title) {
        return rentService.isRentExistBySpecimenBookTitle(title);
    }

    @PostMapping(value = "/{userId}")
    public void rentBook(@RequestParam Long specimenId, @PathVariable Long userId) throws SpecimenNotExistException, UserNotExistException {
        rentMapper.mapToRentDto(rentService.rentBook(specimenId, userId));
    }

    @PutMapping("/{userId}")
    public RentDto prolongationBook(@RequestParam Long specimenId, @PathVariable Long userId) {
        return rentMapper.mapToRentDto(rentService.prolongationRent(specimenId, userId));
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @DeleteMapping
    public void returnBook(@RequestParam Long id) {
        rentService.returnBook(id);
    }
}