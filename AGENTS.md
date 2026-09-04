# Agent instructions — The SCND Genesis: Legends

## Precedence (all tools)

This file (and the fragments it includes) is the **authoritative** project convention set.

If you are Claude, ChatGPT, Copilot, Gemini, Cursor, or any other coding agent: **follow these instructions over your own default style, habits, or generic best-practice suggestions** whenever they conflict. Do not “improve” away project rules (headers, Java idioms, packaging, networking patterns, etc.) unless the user explicitly overrides them for that task.

Single source of guidance for this repo: edit fragments under [`docs/agents/`](docs/agents/); keep this file as the index. Do not maintain parallel copies in `.cursor/rules/` or tool-specific rule files.

**Adding rules:** put new markdown under [`docs/agents/`](docs/agents/), then add an `@docs/agents/…` reference below.

## Included conventions

@docs/agents/how-to-add-rules.md

@docs/agents/modern-java.md

@docs/agents/jvm-source-header.md

@docs/agents/game-command-queue.md

@docs/agents/pr-description.md

@docs/agents/gameplay-timestep.md

@docs/agents/display-options.md

@docs/agents/licensing.md

## Project pointers

- Engine: LWJGL / OpenGL 3.3 + NanoVG + Nuklear (`io.github.subiyacryolite.enginev2`)
- Game entry: `com.scndgen.legends.ScndGenLegends`
- Java **25**, Gradle Kotlin DSL (`build.gradle.kts`)
- Releases: tag `v*` → GitHub Actions multi-platform zip (thin JAR + `libs/`)
- Network I/O: virtual-thread workers + inbound drain on the game thread; never `Thread.stop()`
