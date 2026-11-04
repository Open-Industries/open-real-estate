package org.openindustries.realestate.lease;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateLeaseTemplateRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "RESIDENTIAL|COMMERCIAL|SHORT_TERM") String templateType,
        @Positive int termMonths) {
}
