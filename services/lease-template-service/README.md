# Lease Template Service

Bounded context: **Lease Templates** — Lease template catalogue by type and term.

Alignment: Leasing master data. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/lease-templates` |
| Owns data | LeaseTemplate records |

Run locally: `mvn -pl services/lease-template-service quarkus:dev`.
