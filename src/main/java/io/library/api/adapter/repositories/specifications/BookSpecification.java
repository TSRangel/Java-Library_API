package io.library.api.adapter.repositories.specifications;

import io.library.api.adapter.DTOs.requests.BookFilterDTO;
import io.library.api.domain.entities.Author;
import io.library.api.domain.entities.Book;
import io.library.api.domain.enums.Genre;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookSpecification {
    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) -> {
            query.distinct(true);

            Join<Book, Author> join = root.join("author");

            return cb.like(cb.lower(join.get("name")), "%" + author.toLowerCase() + "%");
        };
    }

    public static Specification<Book> publicationDateEqual(LocalDate publicationDate) {
        return (root, query, cb) ->
                cb.equal(root.get("publicationDate"), publicationDate);
    }

    public static Specification<Book> genreEqual(Genre genre) {
        return (root, query, cb) ->
                cb.equal(root.get("genre"), genre.name());
    }

    public static Specification<Book> priceLessThan(BigDecimal price) {
        return (root, query, cb) ->
                cb.lessThan(root.get("price"), price);
    }

    public static Specification<Book> filterSpecification(BookFilterDTO filter) {
        List<Specification<Book>> spec = new ArrayList<>();

        Optional.ofNullable(filter.getTitle())
                .map(BookSpecification::titleLike)
                .ifPresent(spec::add);

        Optional.ofNullable(filter.getAuthor())
                .map(BookSpecification::hasAuthor)
                .ifPresent(spec::add);

        Optional.ofNullable(filter.getPublication())
                .map(BookSpecification::publicationDateEqual)
                .ifPresent(spec::add);

        Optional.ofNullable(filter.getGenre())
                .map(BookSpecification::genreEqual)
                .ifPresent(spec::add);

        Optional.ofNullable(filter.getPrice())
                .map(BookSpecification::priceLessThan)
                .ifPresent(spec::add);

        return spec.stream().reduce(Specification.where(null), Specification::and);
    }
}
