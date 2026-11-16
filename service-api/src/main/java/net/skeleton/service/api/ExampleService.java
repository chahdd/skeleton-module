package net.thevpc.skeleton.service.api;

import net.thevpc.skeleton.infra.ExampleEntity;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Example business logic in the Service-API layer.
 *
 * The Service-API layer contains:
 * - Business service interface definitions
 * - Business operation contracts
 * - Service-level DTOs
 *
 * This layer defines business operations that orchestrate
 * data access and implement business rules.
 */
public interface ExampleService {

    ExampleEntity create(ExampleEntity entity);

    Optional<ExampleEntity> findById(Long id);

    List<ExampleEntity> findAll();

    ExampleEntity update(Long id, ExampleEntity entity);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
