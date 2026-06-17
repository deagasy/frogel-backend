# Frogel — Backend

Frogel is a calm long-term goals tracker built around the idea of a gentle expedition toward a bigger goal.

The product is not a todo-list, task manager, Jira, Trello, Excel, or KPI system.
It helps users move toward meaningful goals through small steps, daily focus, measurable progress, deadlines, and soft attention reminders.

Current version is prepared for the first private demo / early product showing.

## Current status

Implemented:

* registration and login;
* JWT authentication;
* protected API;
* user profile;
* logout;
* user-based goal isolation;
* goal CRUD;
* goal step CRUD;
* normal and measurable steps;
* automatic progress calculation;
* deadlines for goals and steps;
* Today Plan;
* Attention block;
* PostgreSQL persistence;
* Docker-based local database;
* separate dev and test databases.

## Tech stack

Backend:

* Java 17
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* PostgreSQL
* Docker Compose
* Maven Wrapper

Frontend:

* HTML
* CSS
* JavaScript

The frontend is maintained in a separate repository:

* https://github.com/deagasy/frogel-frontend

## Requirements

Install before running the project:

* Java 17
* Docker Desktop
* Git
* Browser
* Maven is not required separately because the project includes Maven Wrapper

Check Java version:

```powershell
java -version
```

Expected: Java 17.

## Start PostgreSQL with Docker

Make sure Docker Desktop is running.

From the backend project root:

```powershell
docker compose up -d
```

Check that PostgreSQL is running:

```powershell
docker ps
```

Expected container:

```text
frogel-postgres
```

PostgreSQL runs on port:

```text
5433
```

Databases:

```text
frogel_dev
frogel_test
```

## Start backend with dev profile

From the backend project root:

```powershell
cd C:\Users\diana\Downloads\frogel-backend
```

If Maven uses the wrong Java version, set Java 17 manually:

```powershell
$env:java_home="c:\users\diana\appdata\local\programs\eclipse adoptium\jdk-17.0.19.10-hotspot"
$env:path="$env:java_home\bin;$env:path"
```

Set Spring profile:

```powershell
$env:spring_profiles_active="dev"
```

Start backend:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend will be available at:

```text
http://localhost:8080
```

Health/basic API check:

```text
http://localhost:8080/goals
```

Important: `/goals` is protected after Accounts v1. Without a token, this endpoint may return an authorization error. That is expected.

## Start frontend

Open the frontend project:

```powershell
cd C:\Users\diana\Downloads\frogel-frontend
```

Start it the same way as the current static frontend workflow, for example through Live Server / local static server.

Open:

```text
auth.html
```

Typical local URL depends on the frontend server, for example:

```text
http://localhost:5500/auth.html
```

After login, the frontend stores JWT token in localStorage and sends it to the backend in the Authorization header.

Expected token key:

```text
frogel_auth_token
```

## Run tests

From the backend project root:

```powershell
.\mvnw.cmd test
```

If Maven fails because it uses the wrong Java version, set Java 17 first:

```powershell
$env:java_home="c:\users\diana\appdata\local\programs\eclipse adoptium\jdk-17.0.19.10-hotspot"
$env:path="$env:java_home\bin;$env:path"
.\mvnw.cmd test
```

## Test DB and test profile

The local Docker setup creates two databases:

```text
frogel_dev
frogel_test
```

Use `frogel_dev` for local manual development.

Use `frogel_test` for automated tests and safe test runs.

Profiles:

```text
dev
test
```

Dev profile config:

```text
src/main/resources/application-dev.properties
```

Test profile config:

```text
src/main/resources/application-test.properties
```

The test profile must never use the dev database, because tests should not modify local manual demo data.

## Manual persistence smoke test

Use this check after backend/database changes.

1. Start PostgreSQL:

```powershell
docker compose up -d
```

2. Start backend with dev profile.

3. Register or login through the frontend.

4. Create a new goal.

5. Open the main page and check that the goal is visible.

6. Restart backend.

7. Open the app again.

Expected result:

```text
The created goal is still present after backend restart.
```

## Demo scenario

Use this flow for the first product showing.

### 1. Open Frogel

Open the auth page and login with a prepared demo account.

Show that Frogel starts calmly and does not look like a task manager or KPI dashboard.

### 2. Show main page

Show:

* goals block;
* Today Plan;
* Attention block.

Explain:

```text
Frogel helps you move toward long-term goals through small calm steps.
```

### 3. Create a goal

Create a goal, for example:

```text
Prepare Frogel for first demo
```

Add a deadline.

### 4. Add steps

Add one normal step:

```text
Collect feedback
```

Add one measurable step:

```text
Check release scenarios
0 из 5 сценариев
```

### 5. Add progress

Add progress to the measurable step.

Show that the goal progress updates automatically.

### 6. Add step to Today Plan

Add one step to Today Plan.

Explain:

```text
Today Plan is a small daily focus, not a full todo-list.
```

### 7. Show Attention block

Show a goal or step with an upcoming deadline or “Не обновлялось N дней”.

Explain that this is a soft reminder to return to something important.

### 8. Show profile and logout

Open profile.

Show:

* display name;
* email;
* logout.

Logout and return to auth page.

## Troubleshooting

### Maven fails: release version 17 not supported

Reason: Maven uses Java 11 or another older Java version.

Fix:

```powershell
$env:java_home="c:\users\diana\appdata\local\programs\eclipse adoptium\jdk-17.0.19.10-hotspot"
$env:path="$env:java_home\bin;$env:path"
.\mvnw.cmd -version
```

Expected:

```text
Java version: 17
```

Then start backend again:

```powershell
$env:spring_profiles_active="dev"
.\mvnw.cmd spring-boot:run
```

### Backend cannot connect to database

Check Docker:

```powershell
docker ps
```

If `frogel-postgres` is not running:

```powershell
docker compose up -d
```

### Port 8080 is already in use

Another backend process is already running.

Stop the old process or close the terminal where backend is running.

Then start backend again:

```powershell
.\mvnw.cmd spring-boot:run
```

### Frontend redirects to auth page

This is expected if there is no JWT token in localStorage.

Login again through `auth.html`.

### API returns 401 or 403

This usually means:

* user is not logged in;
* token is missing;
* token is invalid;
* user tries to access another user’s data.

For protected endpoints, the frontend must send:

```text
Authorization: Bearer <token>
```

### README shows strange symbols like вЂ”

This is an encoding problem.

Save README as UTF-8.

## What is not included in the current version

Current version is for the first private demo / early product showing.

Not included yet:

* password reset;
* email confirmation;
* Google login;
* public user profiles;
* avatars;
* admin panel;
* production deployment;
* mobile app;
* offline mode;
* notifications;
* collaborative goals;
* advanced analytics;
* paid plans;
* account deletion;
* full accessibility audit;
* full security audit.

## Notes for development

Do not commit files with secrets.

Do not commit:

```text
application-local.properties
.env
```

Before showing the product, check:

```powershell
git status
```

Recommended release preparation flow:

```text
1. Start Docker
2. Start backend
3. Start frontend
4. Register/login
5. Create goal
6. Add steps
7. Add Today Plan item
8. Check profile/logout
9. Check user isolation
```
