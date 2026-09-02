# Gameplay timestep

Match **sim** stays on the historic clocks: **60 Hz** (`DT_60` / `tick60` — ATB, timer, fury meter, AI) and **30 Hz** (`DT_30` / `tick30` — attack/fury resolve). Do not run those steps at display rate.

**Animation** (stage parallax, bob, floaters, fades) steps **once per presented GLFW frame** in `Mode.tick` → `processAnimation()`, using last-frame delta:

`animationScale = clamp(deltaSeconds, 0, 0.25) * 60`

**1.0** is one historic 60 Hz frame. Wall-clock motion stays the same at any refresh:

- 30 Hz (~0.033 s / 33 ms) → scale ~2
- 60 Hz (~0.017 s / 16.7 ms) → scale ~1
- 120 Hz (~0.008 s / 8.3 ms) → scale ~0.5

Historic 30 Hz stage motion uses `animationScale * 0.5` on top of that.

`Mode.reset()` / `GamePlay.reset()` are for **new matches, new instances, and story boards**. Do not wipe session clocks from the present loop. `newInstance()` is reset plus a GPU reload; `startFight()` is `reset()` + `resetGame()` only.
