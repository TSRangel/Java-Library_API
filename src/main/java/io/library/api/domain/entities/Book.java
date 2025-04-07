package io.library.api.domain.entities;

import io.library.api.domain.enums.Genre;
import io.library.api.domain.valueObjects.ISBN;
import io.library.api.domain.valueObjects.Price;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_book")
public class Book extends BaseEntity{
    private ISBN isbn;
    private String title;
    private LocalDate publicationDate;
    private Genre genre;
    private Price price;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Builder
    public Book(String isbn, String title, LocalDate publicationDate, Genre genre, Double price, Author author) {
        this.isbn = new ISBN(isbn);
        this.title = title;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.price = new Price(price);
        this.author = author;
    }
}
