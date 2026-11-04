package org.openindustries.realestate.appraisal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "RESIDENTIAL|COMMERCIAL|LAND|INDUSTRIAL") String propertyType,
        @Positive int requiredMaxValuationGapPct,
        @Positive long requiredMaxValueMinor) {
}
