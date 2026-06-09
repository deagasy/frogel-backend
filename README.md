# Frogel — Backend

Spring Boot REST API for the Frogel goal tracker.

## Requirements

- Java 17+
- Maven (or use the included `mvnw` wrapper)

## Run locally

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Run tests

```bash
./mvnw test
```

## Notes

- Requires JDK 17. If Maven fails because JAVA_HOME points to JDK 11, set JAVA_HOME to a JDK 17 installation before running `./mvnw test`.
- Frontend is maintained in a separate repository: [frogel-frontend](https://github.com/deagasy/frogel-frontend)
- Do not commit `application-local.properties` or any file containing secrets.
