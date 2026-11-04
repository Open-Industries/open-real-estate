package org.openindustries.realestate.escrow;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class EscrowRepository {
    private final ConcurrentHashMap<UUID, EscrowAccount> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<EscrowRelease> records = new CopyOnWriteArrayList<>();

    public EscrowAccount saveAccount(EscrowAccount account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<EscrowAccount> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public EscrowRelease saveRecord(EscrowRelease record) {
        records.add(record);
        return record;
    }

    public List<EscrowRelease> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
