package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Author Kamil Seweryn
 */

@Repository
@Transactional
public interface RentRepository extends JpaRepository<Rent, Long> {

    @Override
    List<Rent> findAll();

    List<Rent> findAllByUserId(Long id);

    Rent findBySpecimenId(Long specimenId);

    Rent findBySpecimenIdAndUserId(Long specimenId, Long userId);

    @Override
    Optional<Rent> findById(Long id);

    @Override
    Rent save(Rent rent);

    @Override
    void deleteById(Long rentId);
}