# Appraisal Rules Service

Bounded context: **Appraisal Rules** — Decision rules binding property types to value caps and valuation-gap envelopes.

Alignment: Valuation governance as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/appraisal-rules` |
| Owns data | AppraisalRule rules |
| Publishes | `appraisal.activated` |

Run locally: `mvn -pl services/appraisal-rules-service quarkus:dev`.
