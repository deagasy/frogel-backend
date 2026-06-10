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

## Docker + PostgreSQL

Make sure Docker Desktop is running.

Start PostgreSQL:

```powershell
docker compose up -d

PostgreSQL runs on port 5433.
```

Databases:

- frogel_dev
- frogel_test

Start backend with dev profile

If Maven uses the wrong Java version, set Java 17 manually:

$env:JAVA_HOME="C:\Users\diana\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.19.10-hotspot"
$env:Path="$env:JAVA_HOME\bin;$env:Path"


Start backend:

.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"

Check API:

http://localhost:8080/goals

Manual persistence smoke test
- Start PostgreSQL with Docker Compose.
- Start backend with the dev profile.
- Create a goal through the API or frontend.
- Open /goals.
- Restart backend.
- Open /goals again.

Expected result: the created goal is still present after backend restart.

## Notes

- Requires JDK 17. If Maven fails because JAVA_HOME points to JDK 11, set JAVA_HOME to a JDK 17 installation before running `./mvnw test`.
- Frontend is maintained in a separate repository: [frogel-frontend](https://github.com/deagasy/frogel-frontend)
- Do not commit `application-local.properties` or any file containing secrets.
