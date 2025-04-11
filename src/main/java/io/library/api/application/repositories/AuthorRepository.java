package io.library.api.application.repositories;

import io.library.api.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByNameContaining(String name);
}
