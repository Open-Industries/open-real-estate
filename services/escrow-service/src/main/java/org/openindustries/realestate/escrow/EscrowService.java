package org.openindustries.realestate.escrow;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EscrowService {

    @Inject
    EscrowRepository repository;

    public EscrowAccount open(OpenEscrowAccountRequest request) {
        return repository.saveAccount(EscrowAccount.open(request.propertyId(), request.escrowAmountMinor()));
    }

    public EscrowAccount get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "EscrowAccount not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public EscrowRelease consume(UUID accountId, CreateEscrowReleaseRequest request) {
        EscrowAccount account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "EscrowAccount is not ACTIVE");
        }
        if (request.amountMinor() > account.escrowAmountMinor) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.amountMinor()
                    + " exceeds available " + account.escrowAmountMinor);
        }
        account.escrowAmountMinor -= request.amountMinor();
        repository.saveAccount(account);
        return repository.saveRecord(EscrowRelease.of(accountId, request.releaseType(), request.amountMinor(), account.escrowAmountMinor));
    }

    public List<EscrowRelease> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
