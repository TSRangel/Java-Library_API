package io.library.api.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
//    @CreationTimestamp -- via hibernate
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
//    @UpdateTimestamp -- via hibernate
    @LastModifiedDate
    private LocalDateTime updatedAt;


    public BaseEntity(UUID id) {
        this.id = id;
    }
}
