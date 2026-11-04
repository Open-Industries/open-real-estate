package org.openindustries.realestate.appraisal;

import java.util.UUID;

/**
 * AppraisalRule aggregate — a decision rule of this core: for one propertyType, it guarantees a
 * capacity (maxValueMinor) and a threshold (maxValuationGapPct), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class AppraisalRule {
    public UUID id;
    public String name;
    public String propertyType;
    public long maxValueMinor;
    public int maxValuationGapPct;
    public int priority;
    public String status;

    public static AppraisalRule create(String name, String propertyType, long maxValueMinor, int maxValuationGapPct, int priority) {
        AppraisalRule r = new AppraisalRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.propertyType = propertyType;
        r.maxValueMinor = maxValueMinor;
        r.maxValuationGapPct = maxValuationGapPct;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
