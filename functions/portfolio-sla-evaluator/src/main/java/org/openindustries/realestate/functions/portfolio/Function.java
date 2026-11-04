package org.openindustries.realestate.functions.portfolio;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares vacancy days and occupancy against property targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String propertyId, int measuredVacancyDays, int targetVacancyDays,
                      double measuredOccupancyPct, double targetOccupancyPct) {
    }

    public record Verdict(String propertyId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict portfolioSlaEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredVacancyDays() > kpi.targetVacancyDays()) {
            findings.add("vacancyDays " + kpi.measuredVacancyDays() + " exceeds target " + kpi.targetVacancyDays());
        }
        if (kpi.measuredOccupancyPct() < kpi.targetOccupancyPct()) {
            findings.add("occupancyPct " + kpi.measuredOccupancyPct() + " below target " + kpi.targetOccupancyPct());
        }
        return new Verdict(kpi.propertyId(), findings.isEmpty(), findings);
    }
}
