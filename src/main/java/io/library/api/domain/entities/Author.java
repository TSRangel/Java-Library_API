package io.library.api.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_author")
public class Author extends BaseEntity{
    private String name;
    private LocalDate birthDate;
    private String nationality;
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    private Set<Book> books = new HashSet<>();

    @Builder
    public Author(String name, LocalDate birthDate, String nationality, Book... books) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        Arrays.stream(books).forEach(this::addBook);
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }
}
