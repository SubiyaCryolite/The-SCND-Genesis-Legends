# Modern Java style

When writing or editing JVM sources in this project (Java 21):

- Prefer **`var`** for local variables when the type is obvious from the right-hand side.
- Prefer **try-with-resources** for `AutoCloseable` / `Closeable` (sockets, streams, channels). Do not leave manual `close()` in `finally` when try-with-resources fits.
- Prefer **switch expressions**, arrow `case` labels, and exhaustive switches over legacy fall-through switches.
- Prefer **records** for immutable data carriers; **sealed** types when a closed hierarchy helps.
- Prefer **`List.of` / `ArrayList`**, `Objects.requireNonNull(Else)`, and clear null-handling over outdated utilities.
- Prefer **virtual threads** (`Thread.ofVirtual()`) or structured executors for blocking I/O workers; never `Thread.stop()`.
- Prefer **text blocks** for multi-line strings when readable.
- Keep the project GPL file header on new sources (see [jvm-source-header.md](jvm-source-header.md)).

Do not modernize unrelated files in drive-by refactors; apply these idioms in code you touch.
