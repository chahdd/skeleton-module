package net.thevpc.skeleton.dal.api;

import net.thevpc.skeleton.infra.ExampleEntity;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ExampleEntity in the DAL-API layer.
 *
 * The DAL-API layer contains:
 * - Repository interface definitions
 * - Custom query methods
 * - Data access contracts
 *
 * This layer defines WHAT data operations are available,
 * but not HOW they are implemented.
 */
public interface ExampleRepository {

    ExampleEntity save(ExampleEntity entity);

    Optional<ExampleEntity> findById(Long id);

    List<ExampleEntity> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
