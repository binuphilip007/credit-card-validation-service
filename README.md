# Credit Card Validation Service

Standalone Spring Boot service for the credit-card payment status API described by the OpenAPI contract.

## Project details

- Java 17
- Spring Boot 3.1.5
- Maven
- In-memory payment status data for local development
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
