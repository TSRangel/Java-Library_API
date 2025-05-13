package io.library.api.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tb_user")
public class User extends BaseEntity{
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
