package org.openindustries.realestate.escrow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateEscrowReleaseRequest(
        @NotBlank @Pattern(regexp = "DEPOSIT|INSPECTION|CLOSING|REPAIR") String releaseType,
        @Positive long amountMinor) {
}
