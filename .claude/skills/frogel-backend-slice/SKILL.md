---
name: frogel-backend-slice
description: Use this skill when making a small, safe Frogel backend change in Java Spring Boot.
---

# Frogel Backend Slice Workflow

Use this workflow for backend changes in Frogel.

## Before editing

1. Read CLAUDE.md.
2. Run or ask for:

```powershell
git status
```

3. Identify the slice type:

   * endpoint change
   * DTO change
   * storage logic
   * validation
   * bug fix
   * refactoring

4. Name the exact Java files that should change.

## Safety rules

* Do not touch frontend files.
* Do not change endpoint paths unless explicitly asked.
* Do not change JSON field names unless explicitly asked.
* Do not introduce database/auth/users unless explicitly asked.
* Do not over-engineer.
* Keep changes small and easy to review.

## Implementation style

Prefer:

* clear Java methods
* simple DTOs
* small controller changes
* simple validation
* readable storage logic
* predictable HTTP statuses

Avoid:

* large refactors
* new frameworks
* hidden behavior changes
* complex abstractions
* changing unrelated features

## After editing

Provide:

1. Changed files.
2. Short summary.
3. API changes, if any.
4. Test command.
5. Manual check.

Recommended checks:

```powershell
.\mvnw.cmd test
```

and if needed:

```powershell
.\mvnw.cmd spring-boot:run
```

Do not commit unless the user explicitly asks.
