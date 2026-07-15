# Expanded Bad Practice Catalog

This framework now combines multiple categories of poor engineering practices:

- hardcoded secrets and tokens
- mutable global state
- resource leaks from unclosed streams
- broad exception swallowing
- sleep-based synchronization
- string concatenation for SQL/JSON-like payloads
- hidden dependencies on config files and environment state
- meaningless assertions
- shared mutable collections
- duplicate logic and weak encapsulation
- non-deterministic behavior from timestamps and randomness
