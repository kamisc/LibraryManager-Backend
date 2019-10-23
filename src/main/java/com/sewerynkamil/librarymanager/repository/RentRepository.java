package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
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

    List<Rent> findAllByReturnDate(LocalDate date);

    Rent findBySpecimenId(Long id);

    @Override
    Optional<Rent> findById(Long id);

    @Override
    Rent save(Rent rent);

    @Override
    void deleteById(Long id);
}