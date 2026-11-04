package org.openindustries.realestate.lease;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class LeaseTemplateRepository {
    private final ConcurrentHashMap<UUID, LeaseTemplate> store = new ConcurrentHashMap<>();

    public LeaseTemplate save(LeaseTemplate entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<LeaseTemplate> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<LeaseTemplate> findAll() {
        return List.copyOf(store.values());
    }
}
