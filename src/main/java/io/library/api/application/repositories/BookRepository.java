package io.library.api.application.repositories;

import io.library.api.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
