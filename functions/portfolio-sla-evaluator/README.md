# Portfolio SLA Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares vacancy days and occupancy against property targets; one finding per breach.

Invoke: `POST /portfolioSlaEvaluator` with the KPI payload.
