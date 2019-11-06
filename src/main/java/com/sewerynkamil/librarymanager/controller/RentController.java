package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.domain.exceptions.RentNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
import com.sewerynkamil.librarymanager.dto.RentDto;
import com.sewerynkamil.librarymanager.mapper.RentMapper;
import com.sewerynkamil.librarymanager.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping
    public List<RentDto> getAllRents() {
        return rentMapper.mapToRentDtoList(rentService.findAllRents());
    }

    @GetMapping("/user/{userId}")
    public List<RentDto> getAllRentsByUserId(@PathVariable Long userId) {
        return rentMapper.mapToRentDtoList(rentService.findAllRentsByUserId(userId));
    }

    @GetMapping("/date/{date}")
    public List<RentDto> getAllRentsByReturnDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return rentMapper.mapToRentDtoList(rentService.findAllRentsByReturnDate(date));
    }

    @GetMapping("/specimen/{specimenId}")
    public RentDto getOneRentBySpecimenId(@PathVariable Long specimenId) {
        return rentMapper.mapToRentDto(rentService.findOneRentBySpecimenId(specimenId));
    }

    @GetMapping("/rent/{rentId}")
    public RentDto getOneRentById(@PathVariable Long rentId) throws RentNotExistException {
        return rentMapper.mapToRentDto(rentService.findOneRentById(rentId));
    }

    @PostMapping(value = "/{userId}")
    public void rentBook(@RequestParam Long specimenId, @PathVariable Long userId) throws SpecimenNotExistException, UserNotExistException {
        rentMapper.mapToRentDto(rentService.rentBook(specimenId, userId));
    }

    @PutMapping(value = "/{userId}")
    public RentDto prolongationBook(@RequestParam Long specimenId, @PathVariable Long userId) {
        return rentMapper.mapToRentDto(rentService.prolongationRent(specimenId, userId));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping
    public void returnBook(@RequestParam Long id) {
        rentService.returnBook(id);
    }
}