package org.openindustries.realestate.escrow;

import java.util.UUID;

/**
 * EscrowAccount aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class EscrowAccount {
    public UUID id;
        public java.util.UUID propertyId;
    public long escrowAmountMinor;
    public String status;

    public static EscrowAccount open(java.util.UUID propertyId, long escrowAmountMinor) {
        EscrowAccount a = new EscrowAccount();
        a.id = UUID.randomUUID();
        a.propertyId = propertyId;
        a.escrowAmountMinor = escrowAmountMinor;
        a.status = "ACTIVE";
        return a;
    }
}
