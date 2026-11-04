# Property Service

Bounded context: **Properties** — Property registry; the land-registry reference is masked at listing.

Alignment: ISO 19152 LADM posture; RESO field discipline. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/properties` |
| Owns data | Property records |
| Publishes | `property.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/property-service quarkus:dev`.
