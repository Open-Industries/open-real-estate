package org.openindustries.realestate.appraisal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateAppraisalRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "RESIDENTIAL|COMMERCIAL|LAND|INDUSTRIAL") String propertyType,
        @Positive long maxValueMinor,
        @Positive int maxValuationGapPct,
        @Min(1) @Max(100) int priority) {
}
