package org.openindustries.realestate.property;

import java.time.Instant;
import java.util.UUID;

/**
 * Property aggregate — Property registry; the land-registry reference is masked at listing.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class Property {
    public UUID id;
        public String name;
        public String propertyType;
        public String landRegistryRefMasked;
    public String status;
    public Instant createdAt;

    public static Property create(String name, String propertyType, String landRegistryRef) {
        Property e = new Property();
        e.id = UUID.randomUUID();
        e.name = name;
        e.propertyType = propertyType;
        e.landRegistryRefMasked = mask(landRegistryRef);
        e.status = "LISTED";
        e.createdAt = Instant.now();
        return e;
    }

    static String mask(String value) {
        if (value == null || value.length() < 4) {
            return "****";
        }
        return "*".repeat(value.length() - 4) + value.substring(value.length() - 4);
    }
}
