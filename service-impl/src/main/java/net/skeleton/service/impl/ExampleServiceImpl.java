package net.skeleton.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.skeleton.dal.api.ExampleRepository;
import net.skeleton.infra.ExampleEntity;
import net.skeleton.service.api.ExampleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for Example business logic in the Service-Impl layer.
 *
 * The Service-Impl layer contains:
 * - Business logic implementation
 * - Transaction management
 * - Calls to DAL API contracts
 * - Integration with DAL
 *
 * Technologies used:
 * - @Transactional for transaction management
 * Resilience policies should be placed on adapters that call remote systems,
 * not on local database writes.
 * - SLF4J for logging
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExampleServiceImpl implements ExampleService {

    private final ExampleRepository repository;

    @Override
    public ExampleEntity create(ExampleEntity entity) {
        log.info("Creating new entity: {}", entity.getName());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        ExampleEntity created = repository.save(entity);
        log.info("Entity created with ID: {}", created.getId());
        return created;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExampleEntity> findById(Long id) {
        log.debug("Finding entity by ID: {}", id);
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExampleEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public ExampleEntity update(Long id, ExampleEntity entity) {
        log.info("Updating entity with ID: {}", id);
        Optional<ExampleEntity> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Entity not found with ID: " + id);
        }
        entity.setId(id);
        entity.setCreatedAt(existing.get().getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());
        ExampleEntity updated = repository.save(entity);
        log.info("Entity updated: {}", updated.getId());
        return updated;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting entity with ID: {}", id);
        repository.deleteById(id);
        log.info("Entity deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

}
