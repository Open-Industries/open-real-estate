package org.openindustries.realestate.lease;

import java.util.UUID;

/** LeaseTemplate aggregate — Lease template catalogue by type and term. */
public class LeaseTemplate {
    public UUID id;
        public String name;
        public String templateType;
        public int termMonths;
    public String status;

    public static LeaseTemplate create(String name, String templateType, int termMonths) {
        LeaseTemplate e = new LeaseTemplate();
        e.id = UUID.randomUUID();
        e.name = name;
        e.templateType = templateType;
        e.termMonths = termMonths;
        e.status = "DRAFT";
        return e;
    }
}
