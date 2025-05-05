package io.library.api.adapter.repositories;

import io.library.api.domain.entities.Book;
import io.library.api.domain.valueObjects.ISBN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByIsbn(ISBN isbn);
}
