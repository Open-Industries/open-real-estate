package org.openindustries.realestate.functions.propertyrisk;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores property risk signals (title issues, market volatility, condition) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String propertyId, int titleIssueIndex, int volatilityIndex, int conditionIndex) {
    }

    public record Score(String propertyId, int score, String decision, String rationale) {
    }

    @Funq
    public Score propertyRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.titleIssueIndex() >= 70) {
            score += 40;
            why.append("titleIssueIndex >= 70; ");
        }
        if (event.volatilityIndex() >= 60) {
            score += 30;
            why.append("volatilityIndex >= 60; ");
        }
        if (event.conditionIndex() >= 50) {
            score += 30;
            why.append("conditionIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.propertyId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
