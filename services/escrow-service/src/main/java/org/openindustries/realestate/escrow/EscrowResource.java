package org.openindustries.realestate.escrow;

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

@Path("/api/v1/escrow")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Escrow", description = "Guarded escrow accounts per property transaction and the releases against them.")
public class EscrowResource {

    @Inject
    EscrowService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a escrowaccount")
    public Response open(@Valid OpenEscrowAccountRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public EscrowAccount get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/releases")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public EscrowRelease consume(@PathParam("id") UUID id, @Valid CreateEscrowReleaseRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/releases")
    @Operation(summary = "List consumption records of an account")
    public List<EscrowRelease> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
