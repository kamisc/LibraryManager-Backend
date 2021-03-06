package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Author Kamil Seweryn
 */

@Repository
@Transactional
public interface SpecimenRepository extends JpaRepository<Specimen, Long> {
    List<Specimen> findAllByBookId(Long id);

    List<Specimen> findAllByStatusAndBookId(String status, Long id);

    @Override
    Optional<Specimen> findById(Long id);

    @Override
    Specimen save(Specimen specimen);

    @Override
    void delete(Specimen specimen);
}