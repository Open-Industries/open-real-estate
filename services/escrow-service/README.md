# Escrow Service

Bounded context: **Escrow** — Guarded escrow accounts per property transaction and the releases against them.

Alignment: Closing/escrow discipline. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/escrow` |
| Owns data | EscrowAccount accounts, EscrowRelease records |
| Publishes | `escrow.consumed`, `escrow.insufficient` |

Run locally: `mvn -pl services/escrow-service quarkus:dev`.
