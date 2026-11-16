# Technology placement guide

The template separates business contracts from framework adapters. Start with the README dependency table before adding a library to a POM.

| Requirement | Template state | Where to extend |
|---|---|---|
| Spring, Servlet, REST | Working example | `app`, `ws-rest` |
| JPA | Working H2 example | `dal-jpa`; database driver and settings in `app` |
| Pyramid | Maven boundaries and architecture tests | Keep each layer's POM dependencies narrow |
| NAF | Core API in `app`; Spring integration under `-Pnaf-spring` | App or outbound adapter package |
| Logback | Supplied by Spring Boot starter logging | `app` configuration |
| Unit and integration tests | JUnit and app wiring/boundary tests | Add behavioral tests with each feature |
| Circuit breaker and short retry | Resilience4j with AOP in `app`; example config | Annotate an outbound adapter method or use a programmatic policy |
| Async messaging | Reserved `dal-messaging` slot | Port in `dal-api`, adapter and broker dependency in `dal-messaging` |
| gRPC | Reserved `ws-grpc` and `service-grpc-cli` slots | Shared `.proto` contract, generated code, server and client adapters |
| Remote REST client | Reserved `service-rest-cli` slot | Implement `service-api` using WebClient |
| MongoDB | Reserved `dal-mongodb` slot | Implement the same `dal-api` port, then select it in `app` |

## POM rules

- Keep protocol and database dependencies out of `infra`, `dal-api`, and `service-api`.
- Put Spring Data JPA in `dal-jpa`; put gRPC libraries in the gRPC transport POMs; put Kafka or another broker library in `dal-messaging`.
- Add an adapter to `app/pom.xml` only when its implementation and configuration exist. The root POM may list a reserved module so it participates in the reactor without loading it into the application.
- Keep Java, Spring Boot, Resilience4j, and plugin versions centralized in the root POM. When adding gRPC or a broker, manage its versions there as well.
- Keep the service facade independent of REST request and response classes. Transport mapping belongs in `ws-*` and remote client modules.

## Database choice

H2 is the example development database. For PostgreSQL, add its driver to `app/pom.xml` and configure the datasource outside source control for deployed environments. Replace `ddl-auto=update` with explicit migrations before using a persistent database. For MongoDB, first implement its adapter and ensure it is selected instead of the JPA adapter for the same port.

## Resilience placement

A circuit breaker and short retry are appropriate for a remote operation with a known timeout and safe retry semantics. They are not a substitute for a durable business workflow retry, a scheduler, or a message queue. If using Resilience4j annotations, keep `spring-boot-starter-aop` and `resilience4j-spring-boot3` in the assembled application. Configure the policy name in `application.properties` and use that same name on the adapter.

## Verification

Run `mvn clean verify` from the template root. `PyramidBoundaryTest` checks compile-time layer direction; `SkeletonApplicationTest` checks that the selected JPA adapter is actually wired and persists through the service facade. These tests cover template architecture and assembly. Each real module needs its own behavioral tests.
