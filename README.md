# Open Real Estate — Reference Real Estate Core Platform

A **standard, generic real estate core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> releases from a property's escrow account never exceed the escrowed amount — a release that would overrun it is rejected (422) and records nothing; closings are always fully evidenced.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| ISO 19152 (LADM) | Land Administration Domain Model | `docs/domain/domain-standards.md` mapping (parties/rights/parcels posture) |
| RESO Data Dictionary | Real-estate data standards (MLS) | Property/listing field posture |
| ISO 9836 | Performance standards — floor area | Area measurement posture |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `property-service` | 8081 | ISO 19152 LADM posture; RESO field discipline | Property registry; the land-registry reference is masked at listing. |
| `escrow-service` | 8082 | Closing/escrow discipline | Guarded escrow accounts per property transaction and the releases against them. |
| `appraisal-rules-service` | 8083 | Valuation governance as auditable rules | Decision rules binding property types to value caps and valuation-gap envelopes. |
| `lease-template-service` | 8084 | Leasing master data | Lease template catalogue by type and term. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `property-risk-scorer` | Event / HTTP | Scores property risk signals (title issues, market volatility, condition) with an auditable rationale. |
| `portfolio-sla-evaluator` | Event / HTTP | Compares vacancy days and occupancy against property targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — releases from a property's escrow account never exceed the escrowed amount — a release that would overrun it is rejected (422) and records nothing; closings are always fully evidenced.
3. **Privacy by design** — Land-registry references are validated at listing and never stored or returned in clear — only a masked form is kept. Parties' personal data stays in adopter CRM/legal systems; all data here is synthetic.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/property-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `appraisal-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `escrow-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
