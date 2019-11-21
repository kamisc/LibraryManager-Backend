package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.User;
import com.sewerynkamil.librarymanager.domain.enumerated.Role;
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
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();

    List<User> findByNameStartsWithIgnoreCase(String name);

    List<User> findBySurnameStartsWithIgnoreCase(String surname);

    List<User> findByEmailStartsWithIgnoreCase(String email);

    @Override
    Optional<User> findById(Long id);

    User findByEmail(String email);

    @Override
    User save(User user);

    @Override
    void delete(User user);

    boolean existsByEmail(String email);

    @Override
    long count();
}