package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.domain.exceptions.RentNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.domain.exceptions.UserNotExistException;
import com.sewerynkamil.librarymanager.dto.RentDto;
import com.sewerynkamil.librarymanager.mapper.RentMapper;
import com.sewerynkamil.librarymanager.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<RentDto> getAllRents() {
        return rentMapper.mapToRentDtoList(rentService.findAllRents());
    }

    @GetMapping("/user/{userId}")
    public List<RentDto> getAllRentsByUserId(@PathVariable Long userId) {
        return rentMapper.mapToRentDtoList(rentService.findAllRentsByUserId(userId));
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

    @DeleteMapping("/{userId}")
    public void returnBook(@RequestParam Long specimenId, @PathVariable Long userId) {
        rentService.returnBook(specimenId, userId);
    }
}