package net.skeleton.ws.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.skeleton.infra.ExampleEntity;
import net.skeleton.service.api.ExampleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for Example entities in the WS-REST layer.
 *
 * The WS-REST layer contains:
 * - REST API controllers
 * - HTTP request/response handling
 * - API documentation (OpenAPI/Swagger)
 * - Input validation
 *
 * Technologies used:
 * - Spring Web MVC for REST endpoints
 * - OpenAPI/Swagger for API documentation
 * - Jakarta Validation for input validation
 * - SLF4J for logging
 *
 * This layer exposes business services as REST APIs.
 */
@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Example API", description = "REST API for managing Example entities")
public class ExampleController {

    private final ExampleService exampleService;

    @PostMapping
    @Operation(summary = "Create a new example entity")
    public ResponseEntity<ExampleEntity> create(@Valid @RequestBody ExampleEntity entity) {
        log.info("REST: Creating example entity");
        ExampleEntity created = exampleService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get example entity by ID")
    public ResponseEntity<ExampleEntity> getById(@PathVariable Long id) {
        log.debug("REST: Getting example entity by ID: {}", id);
        return exampleService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all example entities")
    public ResponseEntity<List<ExampleEntity>> getAll() {
        log.debug("REST: Getting all example entities");
        return ResponseEntity.ok(exampleService.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an example entity")
    public ResponseEntity<ExampleEntity> update(@PathVariable Long id,
                                                @Valid @RequestBody ExampleEntity entity) {
        log.info("REST: Updating example entity with ID: {}", id);
        try {
            ExampleEntity updated = exampleService.update(id, entity);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an example entity")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST: Deleting example entity with ID: {}", id);
        exampleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get total count of example entities")
    public ResponseEntity<Long> count() {
        log.debug("REST: Counting example entities");
        long count = exampleService.count();
        return ResponseEntity.ok(count);
    }
}
