package net.skeleton.dal.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ExampleJpaEntity.
 */
@Repository
public interface ExampleJpaRepositorySpring extends JpaRepository<ExampleJpaEntity, Long> {
    // Spring Data JPA will automatically implement basic CRUD operations
    // Add custom query methods here if needed
}
