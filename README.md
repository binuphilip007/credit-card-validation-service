# Credit Card Validation Service

Standalone Spring Boot service for the credit-card payment status API described by the OpenAPI contract.

## Project details

- Java 17
- Spring Boot 3.1.5
- Maven
- H2 in-memory database, seeded with payment cards on startup
- OpenAPI Generator Maven Plugin

## Generate OpenAPI artifacts

From this project directory:

```powershell
& "C:\Binu\Softwares\apache-maven-3.9.16\bin\mvn.cmd" clean generate-sources
```

Generated API interfaces and models are written to:

```text
target/generated-sources/openapi
```

Do not manually copy generated files into `src/main/java`. The Maven plugin regenerates them from the contract.

## Run

```powershell
& "C:\Binu\Softwares\apache-maven-3.9.16\bin\mvn.cmd" spring-boot:run
```

The service is configured for port `9090` and the API base path is:

```text
http://localhost:9090/host/credit-card-payment-api
```

The endpoint defined by the contract is:

```text
POST http://localhost:9090/host/credit-card-payment-api/payment-status
```

The service currently uses deterministic in-memory payment statuses:

```text
CC123456789 -> APPROVED
CC987654321 -> REJECTED
```

Any other payment reference returns `404 Payment not found`.

## Database

Payment cards are stored in an H2 in-memory database using the same repository-interface, repository-implementation, and Spring Data JPA repository pattern as `car-booking-service`:

```text
repository/PaymentCardRepository.java       (port)
repository/PaymentCardJpaRepository.java    (Spring Data JPA)
repository/impl/PaymentCardRepositoryImpl.java
```

On startup, `PaymentCardDataInitializer` seeds the two known cards above if the table is empty. The H2 console is enabled at:

```text
http://localhost:9090/h2-console
```

Use this JDBC URL:

```text
jdbc:h2:mem:creditcarddb
```

Username: `sa`
Password: empty

## Logging

Logging is configured in `src/main/resources/logback-spring.xml` with:

- A console appender.
- A rolling file appender writing to `logs/credit-card-validation-service.log`, rotating at 10 MB, retained for 14 days, capped at 200 MB total.

Both appenders include the trace ID and span ID in each log line. Controller, service, and exception-handler logs cover received requests, resolved payment statuses, and validation/error failures.

## Observability

The service includes Spring Boot Actuator and Micrometer Tracing with the OpenTelemetry bridge:

```text
http://localhost:9090/actuator/health
http://localhost:9090/actuator/metrics
http://localhost:9090/actuator/prometheus
```

Tracing is enabled with full sampling and exports to an OTLP endpoint:

```properties
management.tracing.enabled=true
management.tracing.sampling.probability=1.0
management.otlp.tracing.endpoint=http://localhost:4318/v1/traces
```

An OpenTelemetry Collector must be listening on port `4318` to receive traces. The service starts normally even if no collector is running.
