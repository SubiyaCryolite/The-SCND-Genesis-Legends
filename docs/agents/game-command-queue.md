# Game command queue

Offline and online play share one game-thread command bus.

- Typed intents: sealed [`GameCommand`](../../src/main/java/com/scndgen/legends/command/GameCommand.java) (discriminated union via records).
- Enqueue / drain: [`GameCommandBus`](../../src/main/java/com/scndgen/legends/command/GameCommandBus.java) — drained in `ScndGenLegends.update` before `mode.tick`.
- Wire edge only: [`GameCommandCodec`](../../src/main/java/com/scndgen/legends/command/GameCommandCodec.java) encodes/decodes legacy UTF strings; network workers never call mode APIs.
- Apply once: [`GameCommandApplier`](../../src/main/java/com/scndgen/legends/command/GameCommandApplier.java) is the only place that mutates modes for shared intents.
- `dispatch(cmd)` = enqueue locally (+ broadcast when online and codec can encode). Prefer this for user/game intents.
- `publish(cmd)` = wire only when local UI already applied a *different* local effect than the peer (e.g. initiator cancels session, deselect mirrors to opponent slot).
- Modes must not call `NetworkManager.send` for gameplay; handshake strings in `NetworkClient` / `NetworkServer` stay at the socket edge.
- Offline-only menus (`RenderMainMenu`, story setup) may still call `loadMode` directly; anything that forks offline vs online must go through the bus.
