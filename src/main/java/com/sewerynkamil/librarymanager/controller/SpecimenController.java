package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.domain.enumerated.Status;
import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.dto.SpecimenDto;
import com.sewerynkamil.librarymanager.mapper.SpecimenMapper;
import com.sewerynkamil.librarymanager.service.SpecimenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author Kamil Seweryn
 */

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1/specimens")
public class SpecimenController {
    private SpecimenService specimenService;

    private SpecimenMapper specimenMapper;

    @Autowired
    public SpecimenController(SpecimenService specimenService, SpecimenMapper specimenMapper) {
        this.specimenService = specimenService;
        this.specimenMapper = specimenMapper;
    }

    @GetMapping
    public List<SpecimenDto> getAllSpecmiensForOneBook(@RequestParam Long id) {
        return specimenMapper.mapToSpecimenDtoList(specimenService.findAllSpecimensForOneBookByBookId(id));
    }

    @GetMapping("/status")
    public List<SpecimenDto> getAllSpecmiensForOneBook(@RequestParam Long id, @RequestParam Status status) {
        return specimenMapper.mapToSpecimenDtoList(specimenService.findAllSpecimensForOneBookByStatusAndBookId(status, id));
    }

    @GetMapping("/get")
    public SpecimenDto getOneSpecimen(@RequestParam Long id) throws SpecimenNotExistException {
        return specimenMapper.mapToSpecimenDto(specimenService.findOneSpecimen(id));
    }

    @GetMapping("/count")
    public Long countSpecimenByStatusAndBookId(@RequestParam Status status, @RequestParam Long bookId) {
        return specimenService.countSpecimensByStatusAndBookId(status, bookId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveNewSpecimen(@RequestBody SpecimenDto specimenDto) {
        specimenService.saveNewSpecimen(specimenMapper.mapToSpecimen(specimenDto));
    }

    @PutMapping(value = "/available", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SpecimenDto changeSpecimenStatusToAvailable(@RequestParam Long id) {
        return specimenMapper.mapToSpecimenDto(specimenService.changeSpecimenStatusToAvailable(id));
    }

    @PutMapping(value = "/rented", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SpecimenDto changeSpecimenStatusToRented(@RequestParam Long id) {
        return specimenMapper.mapToSpecimenDto(specimenService.changeSpecimenStatusToRented(id));
    }

    @PutMapping(value = "/lost", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SpecimenDto changeSpecimenStatusToLost(@RequestParam Long id) {
        return specimenMapper.mapToSpecimenDto(specimenService.changeSpecimenStatusToLost(id));
    }

    @DeleteMapping
    public void deleteSpecimen(@RequestParam Long id) {
        specimenService.deleteSpecimen(id);
    }
}