package net.thevpc.skeleton.dal.jpa;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity for ExampleEntity.
 *
 * The DAL-JPA layer contains:
 * - JPA entity classes
 * - JPA repository implementations
 * - Database-specific configurations
 *
 * This is the implementation layer for relational databases using JPA/Hibernate.
 */
@Entity
@Table(name = "example_entities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
