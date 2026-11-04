package org.openindustries.realestate.escrow;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a EscrowAccount. */
public class EscrowRelease {
    public UUID id;
    public UUID accountId;
    public String releaseType;
    public long amountMinor;
    public long remainingAfter;
    public Instant recordedAt;

    public static EscrowRelease of(UUID accountId, String releaseType, long amountMinor, long remainingAfter) {
        EscrowRelease r = new EscrowRelease();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.releaseType = releaseType;
        r.amountMinor = amountMinor;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
