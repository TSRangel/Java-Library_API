package io.library.api.domain.entities;

import io.library.api.domain.enums.Genre;
import io.library.api.domain.valueObjects.ISBN;
import io.library.api.domain.valueObjects.Price;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tb_book")
public class Book extends BaseEntity{
    @Column(nullable = false)
    private ISBN isbn;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false)
    private LocalDate publicationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Column(nullable = false, precision = 12, scale = 2)
    private Price price;
    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_book_author"))
    private Author author;

    public Book(String isbn, String title, LocalDate publicationDate, Genre genre, BigDecimal price, Author author) {
        this.isbn = new ISBN(isbn);
        this.title = title;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.price = new Price(price);
        this.author = author;
    }
}
