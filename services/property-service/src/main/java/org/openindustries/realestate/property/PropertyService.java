package org.openindustries.realestate.property;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PropertyService {

    @Inject
    PropertyRepository repository;

    public Property create(CreatePropertyRequest request) {
        return repository.save(Property.create(request.name(), request.propertyType(), request.landRegistryRef()));
    }

    public Property get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Property not found: " + id));
    }

    public List<Property> list() {
        return repository.findAll();
    }

    public Property reserve(UUID id) {
        Property entity = get(id);
        if ("CLOSED".equals(entity.status)) {
            throw new DomainException(409, "A closed property is terminal");
        }
        if ("RESERVED".equals(entity.status)) {
            throw new DomainException(409, "Property already RESERVED");
        }
        entity.status = "RESERVED";
        return repository.save(entity);
    }

    public Property close(UUID id) {
        Property entity = get(id);
        if ("CLOSED".equals(entity.status)) {
            throw new DomainException(409, "A closed property is terminal");
        }
        if ("CLOSED".equals(entity.status)) {
            throw new DomainException(409, "Property already CLOSED");
        }
        entity.status = "CLOSED";
        return repository.save(entity);
    }
}
