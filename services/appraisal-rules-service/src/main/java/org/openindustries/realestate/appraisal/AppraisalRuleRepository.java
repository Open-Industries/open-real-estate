package org.openindustries.realestate.appraisal;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class AppraisalRuleRepository {
    private final ConcurrentHashMap<UUID, AppraisalRule> store = new ConcurrentHashMap<>();

    public AppraisalRule save(AppraisalRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<AppraisalRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<AppraisalRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<AppraisalRule> activeFor(String propertyType) {
        return store.values().stream()
                .filter(r -> r.propertyType.equals(propertyType) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
