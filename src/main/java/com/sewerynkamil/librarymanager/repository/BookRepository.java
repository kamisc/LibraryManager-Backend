package com.sewerynkamil.librarymanager.repository;

import com.sewerynkamil.librarymanager.domain.Book;
import com.sewerynkamil.librarymanager.domain.enumerated.Category;
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
public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    List<Book> findAll();

    List<Book> findByTitleStartsWithIgnoreCase(String title);

    List<Book> findByAuthorStartsWithIgnoreCase(String author);

    List<Book> findByCategoryStartsWithIgnoreCase(String category);

    @Override
    Optional<Book> findById(Long id);

    Book findByTitle(String title);

    @Override
    Book save(Book book);

    @Override
    void delete(Book book);

    boolean existsByTitle(String title);
}