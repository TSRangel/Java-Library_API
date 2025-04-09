package io.library.api.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public BaseEntity(UUID id) {
        this.id = id;
    }
}
