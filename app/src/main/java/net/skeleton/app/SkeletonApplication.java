package net.skeleton.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main Spring Boot application class.
 *
 * This application follows the Pyramid Architecture pattern:
 * - Infra: Domain models and DTOs
 * - DAL: Data Access Layer (JPA/MongoDB)
 * - Service: Business logic
 * - WS-REST: REST API controllers
 *
 * Technologies included:
 * - Spring Boot (Web, JPA, Data)
 * - REST APIs with OpenAPI/Swagger
 * - Circuit Breaking and Retries (Resilience4j)
 * - Monitoring (Actuator, Micrometer)
 * - Logging (Logback)
 */
@SpringBootApplication
@ComponentScan(basePackages = "net.skeleton")
@EntityScan(basePackages = "net.skeleton.dal.jpa")
@EnableJpaRepositories(basePackages = "net.skeleton.dal.jpa")
public class SkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkeletonApplication.class, args);
    }
}
