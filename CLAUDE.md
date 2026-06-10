# Frogel Backend — Claude Instructions

## Project identity

Frogel is a calm long-term goal tracker built around the metaphor of an expedition to a mountain summit.

It is not a todo list, task manager, Jira, Trello, Excel, or KPI system.

The backend should stay simple and support the product idea: goals, goal parts, measurable progress, deadlines, and calm progress tracking.

## Product concepts

Use practical domain terms in code:

* Goal
* GoalPart
* Deadline
* Progress
* Measurable step

The UI may use the expedition metaphor, but backend code should stay clear and simple.

## Current architecture

This is the standalone backend repo.

Technologies:

* Java
* Spring Boot
* Maven

Current storage:

* in-memory storage
* no database yet
* goals disappear after backend restart

Current implemented backend features:

* Goal CRUD
* Goal Part CRUD
* automatic progress calculation from completed parts
* deadlines model
* measurable goal parts
* PATCH endpoint for adding amount to measurable parts
* CORS config for standalone frontend

Frontend runs separately from:

C:\Users\diana\Downloads\frogel-frontend

Frontend URL:
http://localhost:5500

Backend URL:
http://localhost:8080

## Important current endpoints

Expected current endpoints include:

* GET /goals
* GET /goals/{goalId}
* POST /goals
* DELETE /goals/{goalId}
* POST /goals/{goalId}/parts
* PATCH /goals/{goalId}/parts/{partIndex}
* DELETE /goals/{goalId}/parts/{partIndex}
* PATCH /goals/{goalId}/deadline
* PATCH /goals/{goalId}/parts/{partIndex}/amount

Do not rename endpoints unless explicitly asked.

## Working rules

Always work in small safe slices.

Before changing code:

1. Explain briefly why the change is needed.
2. Identify exact files to touch.
3. Preserve existing API contracts unless explicitly asked.
4. Avoid large rewrites.
5. Keep implementation simple.
6. Do not introduce database/auth/users unless explicitly asked.
7. Do not change frontend files from backend tasks.

After changing code:

1. Summarize changed files.
2. Mention API changes, if any.
3. Suggest backend test/build command.
4. Suggest a small manual API check.
5. Do not commit unless explicitly asked.

## Git rules

Before changes, check:

git status

Do not commit or push unless the user explicitly asks.

## Java version

Use Java 17.

If Maven fails because of Java version, use this temporary PowerShell setup:

```powershell
$env:JAVA_HOME="C:\Users\diana\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.19.10-hotspot"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
java -version
```

## Backend run command

From repo root:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend should start on:

http://localhost:8080

Manual check:

http://localhost:8080/goals

If port 8080 is busy, another backend process is probably already running.

## Backend build/test command

Use:

```powershell
.\mvnw.cmd test
```

## Common risks

* Do not break existing frontend expectations.
* Do not change DTO field names without updating frontend.
* Do not turn simple in-memory storage into a database task unless asked.
* Do not make deadline logic overly complex.
* Do not add authentication/users unless asked.
* Do not return 500 for normal validation errors.
* Keep measurable progress logic simple and predictable.

## Current next product direction

Frontend next slice:
Main Page Layout v1

Backend is not needed for that slice unless a frontend request reveals a backend bug.
