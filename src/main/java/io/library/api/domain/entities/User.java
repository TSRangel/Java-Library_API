package io.library.api.domain.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import io.library.api.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tb_user")
public class User extends BaseEntity{
    @Column(nullable = false)
    private String Login;
    @Column(nullable = false)
    private String Password;
    @Enumerated(value = EnumType.STRING)
    @Type(ListArrayType.class)
    @Column(columnDefinition = "varchar[]")
    private Set<Role> roles;
}
