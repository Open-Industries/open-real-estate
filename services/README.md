# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Properties → Escrow → Appraisal Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `property-service` | 8081 | ISO 19152 LADM posture; RESO field discipline |
| `escrow-service` | 8082 | Closing/escrow discipline |
| `appraisal-rules-service` | 8083 | Valuation governance as auditable rules |
| `lease-template-service` | 8084 | Leasing master data |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/property-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
