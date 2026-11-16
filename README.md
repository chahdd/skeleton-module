# Reusable Pyramid module skeleton

This is a backend **template**, not an implementation of any product module. The `Example` code is a small JPA and REST wiring example to replace when starting a real module. Java 17, Spring Boot 3.3.5, and Maven are configured.

## Layer and dependency rules

| Artifact | Purpose | Allowed module dependencies |
|---|---|---|
| `infra` | Shared domain values and API data types | None |
| `dal-api` | Persistence and outbound capability contracts | `infra` |
| `dal-jpa`, `dal-mongodb`, `dal-messaging` | Swappable outbound adapters | `dal-api`, `infra` |
| `service-api` | Published module facade | `infra` |
| `service-impl` | Business rules and local facade implementation | `service-api`, `dal-api` |
| `service-rest-cli`, `service-grpc-cli` | Remote implementations of the facade | `service-api` |
| `ws-rest`, `ws-grpc` | Inbound transports | `service-api` |
| `app` | Selects and wires implementations and inbound transports | Selected artifacts only |

`ws-*` must call the facade through `service-api`. `service-impl` must call outbound resources through `dal-api`. Concrete adapters meet only in `app`. Other business modules should be accessed through their published facade, not their DAL or implementation.

The REST and JPA example is implemented. `dal-mongodb`, `dal-messaging`, `service-rest-cli`, `service-grpc-cli`, and `ws-grpc` are **reserved slots**. Their POMs establish module boundaries; they contain no adapter implementation or protocol definition. Add a dependency to `app` only after its implementation is present. For gRPC, define a `.proto` contract and code generation before adding a transport library. For messaging, define a port in `dal-api` and implement it in `dal-messaging`.

## Build and run

```bash
mvn clean verify
java -jar app/target/skeleton-module-app-1.0.0-SNAPSHOT.jar
```

After packaging, `app/target/skeleton-module-app-1.0.0-SNAPSHOT.jar` is the runnable JAR. The example API is at `http://localhost:8080/api/examples`; OpenAPI UI is at `http://localhost:8080/api/swagger-ui.html` because the servlet context path is `/api`. H2 is in memory and intended for development only.

The application POM selects `ws-rest`, `service-impl`, and `dal-jpa`. It includes Spring Web/Servlet, the NAF API, Logback through Spring Boot, Resilience4j with AOP, Actuator, and H2. `spring-boot-maven-plugin` repackages the JAR. The optional `naf-spring` Maven profile adds NAF Spring integration when its runtime workspace is provisioned. PostgreSQL, MongoDB, gRPC, and messaging need module-specific adapter implementation and configuration.

## Starting a business module

1. Copy this directory and rename Maven coordinates and Java packages consistently.
2. Replace `ExampleEntity`, the repository port, the service facade and implementation, the JPA adapter, and the REST controller with the module's domain.
3. Keep `infra` and published APIs free of implementation dependencies. Put HTTP, messaging, and database libraries in their adapter POMs.
4. Select one implementation for each DAL port in the app POM. Do not include both JPA and Mongo implementations of the same port unless selection is explicit.
5. Add transport definitions and adapters only where needed. Keep frontend and database migration projects beside the backend as separate project concerns.
6. Run the architecture and assembly tests, then add tests for the module's own behavior.

Circuit breakers and short retries belong around **remote, potentially failing calls**, with deliberate timeout and idempotency rules. The sample local JPA CRUD service does not retry writes. The `remoteService` configuration in `app/src/main/resources/application.properties` is a starting policy for an outbound adapter, not a domain workflow retry mechanism.

NAF is available at the `app` assembly edge. The shared model and published APIs do not depend on NAF runtime classes. Enable the Spring integration with `-Pnaf-spring` after provisioning its runtime workspace. Add NAF-specific behavior in an app or adapter package if a real module needs it. See [NAF_INTEGRATION.md](NAF_INTEGRATION.md) and [TECHNOLOGY_GUIDE.md](TECHNOLOGY_GUIDE.md) for extension guidance.
