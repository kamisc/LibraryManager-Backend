package com.sewerynkamil.librarymanager.controller;

import com.sewerynkamil.librarymanager.domain.exceptions.SpecimenNotExistException;
import com.sewerynkamil.librarymanager.dto.SpecimenDto;
import com.sewerynkamil.librarymanager.mapper.SpecimenMapper;
import com.sewerynkamil.librarymanager.service.SpecimenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping
    public List<SpecimenDto> getAllSpecimensForOneBook(@RequestParam Long bookId) {
        return specimenMapper.mapToSpecimenDtoList(specimenService.findAllSpecimensForOneBookByBookId(bookId));
    }

    @GetMapping("/{bookId}")
    public List<SpecimenDto> getAllSpecimensForOneBook(@RequestParam String status, @PathVariable Long bookId) {
        return specimenMapper.mapToSpecimenDtoList(specimenService.findAllSpecimensForOneBookByStatusAndBookId(status, bookId));
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping("/get/{id}")
    public SpecimenDto getOneSpecimen(@PathVariable Long id) throws SpecimenNotExistException {
        return specimenMapper.mapToSpecimenDto(specimenService.findOneSpecimen(id));
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveNewSpecimen(@RequestBody SpecimenDto specimenDto) {
        specimenService.saveNewSpecimen(specimenMapper.mapToSpecimen(specimenDto));
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SpecimenDto updateSpecimen(@RequestBody SpecimenDto specimenDto) {
        return specimenMapper.mapToSpecimenDto(specimenService.updateSpecimen(specimenMapper.mapToSpecimen(specimenDto)));
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @DeleteMapping
    public void deleteSpecimen(@RequestParam Long id) throws SpecimenNotExistException {
        specimenService.deleteSpecimen(specimenService.findOneSpecimen(id));
    }
}