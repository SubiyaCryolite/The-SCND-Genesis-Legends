# Pull request description template

Use this shape when opening a PR. Keep the title imperative and focused on *why*; fill every section (use `N/A` under Tests when none).

## Title

One line, imperative mood. Prefer feature/fix intent over file lists.

Example: `Unify offline and online play through a game command queue`

## Description

Short prose (2–4 sentences): problem, approach, and what reviewers should focus on. Link agent docs or design notes when they exist.

## New feature

Bullet the user-visible or architectural additions. Omit if the PR is a pure fix/refactor with no new capability — write `N/A` or delete the section only when the change is trivial.

- …

## Tests

How this was verified (manual steps, `./gradlew compileJava`, CI, etc.). If none: `N/A` plus why (e.g. docs-only).

- [ ] …

---

### Copy-paste stub

```markdown
## Description

<problem, approach, review focus>

## New feature

- <capability or N/A>

## Tests

- [ ] <verification step or N/A>
```
