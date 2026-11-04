package org.openindustries.realestate.appraisal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AppraisalRuleService {

    @Inject
    AppraisalRuleRepository repository;

    public AppraisalRule create(CreateAppraisalRuleRequest request) {
        return repository.save(AppraisalRule.create(request.name(), request.propertyType(),
                request.maxValueMinor(), request.maxValuationGapPct(), request.priority()));
    }

    public AppraisalRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "AppraisalRule not found: " + id));
    }

    public List<AppraisalRule> list() {
        return repository.findAll();
    }

    public AppraisalRule activate(UUID id) {
        AppraisalRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.propertyType());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.propertyType());
        }
        for (AppraisalRule rule : candidates) {
            if (rule.maxValuationGapPct <= request.requiredMaxValuationGapPct()
                    && rule.maxValueMinor >= request.requiredMaxValueMinor()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.propertyType() + " but none meets the requirement");
    }
}
