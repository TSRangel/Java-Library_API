package io.library.api.application.specifications;

import io.library.api.adapter.DTOs.requests.AuthorFilterDTO;
import io.library.api.domain.entities.Author;
import io.library.api.domain.entities.Book;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorSpecification {

    public static Specification<Author> nameLike(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Author> nationalityEqual(String nationality) {
        return (root, query, cb) ->
                cb.equal(root.get("nationality"), nationality);
    }

    public static Specification<Author> hasBook(String bookTitle) {
        return (root, query, cb) -> {
            query.distinct(true);

            Join<Author, Book> join = root.join("books");

            return cb.like(cb.lower(join.get("title")), "%" + bookTitle.toLowerCase() + "%");
        };
    }

    public static Specification<Author> filterSpecification(AuthorFilterDTO filter) {
        List<Specification<Author>> spec = new ArrayList<>();

        Optional.ofNullable(filter.getName())
                .map(AuthorSpecification::nameLike)
                .ifPresent(spec::add);

        Optional.ofNullable(filter.getNationality())
                .map(AuthorSpecification::nationalityEqual)
                .ifPresent(spec::add);

        Optional.ofNullable(filter.getTitle())
                .map(AuthorSpecification::hasBook)
                .ifPresent(spec::add);

        return spec.stream().reduce(Specification.where(null), Specification::and);
    }
}
