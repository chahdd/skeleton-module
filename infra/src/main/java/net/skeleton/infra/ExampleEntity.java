package net.thevpc.skeleton.infra;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Example domain entity in the Infra layer.
 *
 * The Infra layer contains:
 * - Domain models
 * - DTOs (Data Transfer Objects)
 * - Value Objects
 * - Constants
 * - Enums
 *
 * This layer has no dependency on other module layers.
 * Keep runtime services and storage annotations outside these shared types.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExampleEntity {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    private EntityStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
