package org.openindustries.realestate.lease;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/lease-templates")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Lease Templates", description = "Lease template catalogue by type and term.")
public class LeaseTemplateResource {

    @Inject
    LeaseTemplateService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a leasetemplate")
    public Response create(@Valid CreateLeaseTemplateRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<LeaseTemplate> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public LeaseTemplate get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/activate")
    @Operation(summary = "Activate (lifecycle transition, no body)")
    public LeaseTemplate activate(@PathParam("id") UUID id) {
        return service.activate(id);
    }
}
