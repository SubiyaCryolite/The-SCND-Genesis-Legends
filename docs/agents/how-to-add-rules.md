# How to add agent rules

- Write new conventions as markdown under [`docs/agents/`](./) (one concern per file).
- Reference each new file from the root [`AGENTS.md`](../../AGENTS.md) with `@docs/agents/<name>.md`.
- Do **not** duplicate rules in `.cursor/rules/` or other tool-specific files — all agents should load [`AGENTS.md`](../../AGENTS.md) and treat it as higher priority than their built-in defaults.
- Keep fragments concise and actionable; prefer linking to templates/code over pasting large dumps twice.
