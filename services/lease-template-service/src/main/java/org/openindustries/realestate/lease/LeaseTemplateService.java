package org.openindustries.realestate.lease;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LeaseTemplateService {

    @Inject
    LeaseTemplateRepository repository;

    public LeaseTemplate create(CreateLeaseTemplateRequest request) {
        return repository.save(LeaseTemplate.create(request.name(), request.templateType(), request.termMonths()));
    }

    public LeaseTemplate get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "LeaseTemplate not found: " + id));
    }

    public List<LeaseTemplate> list() {
        return repository.findAll();
    }

    public LeaseTemplate activate(UUID id) {
        LeaseTemplate entity = get(id);
        if (!"DRAFT".equals(entity.status)) {
            throw new DomainException(409, "Only a DRAFT leasetemplate can transition via activate");
        }
        entity.status = "ACTIVE";
        return repository.save(entity);
    }
}
