package net.thevpc.skeleton.dal.jpa;

import net.thevpc.skeleton.dal.api.ExampleRepository;
import net.thevpc.skeleton.infra.ExampleEntity;
import net.thevpc.skeleton.infra.EntityStatus;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of ExampleRepository.
 *
 * This class bridges the DAL-API interface with the JPA implementation.
 * It converts between domain entities (Infra) and JPA entities.
 */
@Repository
@RequiredArgsConstructor
public class ExampleRepositoryJpaImpl implements ExampleRepository {

    private final ExampleJpaRepositorySpring jpaRepository;

    @Override
    public ExampleEntity save(ExampleEntity entity) {
        ExampleJpaEntity jpaEntity = toJpaEntity(entity);
        ExampleJpaEntity saved = jpaRepository.save(jpaEntity);
        return toDomainEntity(saved);
    }

    @Override
    public Optional<ExampleEntity> findById(Long id) {
        return jpaRepository.findById(id)
            .map(this::toDomainEntity);
    }

    @Override
    public List<ExampleEntity> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomainEntity).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    // Conversion methods
    private ExampleJpaEntity toJpaEntity(ExampleEntity domain) {
        ExampleJpaEntity jpa = new ExampleJpaEntity();
        jpa.setId(domain.getId());
        jpa.setName(domain.getName());
        jpa.setDescription(domain.getDescription());
        jpa.setStatus(domain.getStatus() != null ? domain.getStatus().name() : null);
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        return jpa;
    }

    private ExampleEntity toDomainEntity(ExampleJpaEntity jpa) {
        return ExampleEntity.builder()
            .id(jpa.getId())
            .name(jpa.getName())
            .description(jpa.getDescription())
            .status(jpa.getStatus() != null ? EntityStatus.valueOf(jpa.getStatus()) : null)
            .createdAt(jpa.getCreatedAt())
            .updatedAt(jpa.getUpdatedAt())
            .build();
    }
}
