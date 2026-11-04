package org.openindustries.realestate.escrow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenEscrowAccountRequest(
        @NotNull java.util.UUID propertyId,
        @PositiveOrZero long escrowAmountMinor) {
}
