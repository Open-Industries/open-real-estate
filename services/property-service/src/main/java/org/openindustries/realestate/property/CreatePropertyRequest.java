package org.openindustries.realestate.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreatePropertyRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "RESIDENTIAL|COMMERCIAL|LAND|INDUSTRIAL") String propertyType,
        @NotBlank String landRegistryRef) {
}
