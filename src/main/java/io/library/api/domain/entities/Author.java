package io.library.api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "tb_author")
public class Author extends BaseEntity{
    private String name;
    private LocalDate birthDate;
    private String nationality;
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();

    public Author(UUID id, String name, LocalDate birthDate, String nationality) {
        super(id);
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }
}
