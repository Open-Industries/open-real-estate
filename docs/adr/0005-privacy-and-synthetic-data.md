# ADR-0005: Privacy by design; all reference data is synthetic

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

A public reference core must be safe to clone, demo and audit. ISO 27002 A.5.34.

## Decision

Land-registry references are validated at listing and never stored or returned in clear — only a masked form is kept. Parties' personal data stays in adopter CRM/legal systems; all data here is synthetic. Internal keys are UUIDs and no real personal or confidential data ever enters this repository.

## Consequences

Correlation with production records happens through the adopter's governed processes, not this API.
