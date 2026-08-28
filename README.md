# Credit Card Validation Service

Standalone Spring Boot service for verifying credit card payment status based on an OpenAPI 3.0 contract.

## Tech Stack & Features

- **Java 17 & Spring Boot 3.1.5**
- **Contract-First API Development:** OpenAPI 3.0 specification (`src/main/resources/openapi/credit-card-validation-service.yaml`) compiled via `openapi-generator-maven-plugin`.
- **In-Memory Database:** H2 database seeded on startup with mock payment records.
- **Observability:** Spring Boot Actuator, Micrometer Tracing with OpenTelemetry (OTLP) exporter, and structured Logback logging with trace/span ID propagation.

## Requirements

- Java 17
- Maven 3.8+

## Generate OpenAPI Artifacts

Generate controller interfaces and DTO models from the OpenAPI specification:

```text
mvn clean generate-sources
```

Generated code is placed in:

```text
target/generated-sources/openapi
```

> **Note:** Do not manually copy generated files into `src/main/java`. Maven automatically adds this folder to the build classpath.

## Run

Start the service on port `9090`:

```text
mvn spring-boot:run
```

The service base path is `http://localhost:9090/host/credit-card-payment-api`.

## API Endpoint & Testing

### Retrieve Payment Status (`POST /payment-status`)

```http
POST http://localhost:9090/host/credit-card-payment-api/payment-status
Content-Type: application/json
```

#### Test Scenarios:

1. **Approved Payment (`CC123456789`)**
   ```json
   {
     "paymentReference": "CC123456789"
   }
   ```
   **Response (`200 OK`):**
   ```json
   {
     "lastUpdateDate": "2026-08-28T10:00:00Z",
     "status": "APPROVED"
   }
   ```

2. **Rejected Payment (`CC987654321`)**
   ```json
   {
     "paymentReference": "CC987654321"
   }
   ```
   **Response (`200 OK`):**
   ```json
   {
     "lastUpdateDate": "2026-08-28T10:00:00Z",
     "status": "REJECTED"
   }
   ```

3. **Payment Reference Not Found**
   ```json
   {
     "paymentReference": "CC000000000"
   }
   ```
   **Response (`404 Not Found`):**
   ```json
   {
     "error": "Payment not found"
   }
   ```

4. **Invalid Request (Missing/Blank Reference)**
   ```json
   {
     "paymentReference": ""
   }
   ```
   **Response (`400 Bad Request`):**
   ```json
   {
     "error": "paymentReference is required"
   }
   ```

## Database

Payment card records are persisted in an H2 in-memory database using a port-and-adapter architecture pattern matching `car-booking-service`:

```text
repository/PaymentCardRepository.java       (Port interface)
repository/PaymentCardJpaRepository.java    (Spring Data JPA repository)
repository/impl/PaymentCardRepositoryImpl.java (Adapter implementation)
```

On application startup, `PaymentCardDataInitializer` automatically seeds the two mock cards if the database is empty.

### H2 Console

Access the H2 Console at `http://localhost:9090/h2-console`:
- **JDBC URL:** `jdbc:h2:mem:creditcarddb`
- **Username:** `sa`
- **Password:** *(empty)*

## Logging & Observability

### Logging
Configured via `src/main/resources/logback-spring.xml`:
- **Console Appender**
- **Rolling File Appender:** `logs/credit-card-validation-service.log` (10 MB max file size, 14-day history, 200 MB total cap)
- Log lines automatically include `[traceId, spanId]` for correlation across distributed transactions.

### Observability & Actuator
Spring Boot Actuator endpoints:
```http
GET http://localhost:9090/actuator/health
GET http://localhost:9090/actuator/metrics
GET http://localhost:9090/actuator/prometheus
```

Tracing exports to OpenTelemetry Collector endpoint:
```properties
management.tracing.enabled=true
management.tracing.sampling.probability=1.0
management.otlp.tracing.endpoint=http://localhost:4318/v1/traces
```

## Test

Run unit and integration tests:

```text
mvn test
```
