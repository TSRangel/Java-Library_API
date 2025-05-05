package io.library.api.domain.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tb_author")
public class Author extends BaseEntity{
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, length = 70)
    private String nationality;
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private Set<Book> books = new HashSet<>();
}
