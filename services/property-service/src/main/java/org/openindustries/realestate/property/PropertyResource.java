package org.openindustries.realestate.property;

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

@Path("/api/v1/properties")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Properties", description = "Property registry; the land-registry reference is masked at listing.")
public class PropertyResource {

    @Inject
    PropertyService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a property")
    public Response create(@Valid CreatePropertyRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<Property> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public Property get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/reserve")
    @Operation(summary = "Reserve this property (lifecycle transition, no body)")
    public Property reserve(@PathParam("id") UUID id) {
        return service.reserve(id);
    }

    @POST
    @Path("/{id}/close")
    @Operation(summary = "Close this property (lifecycle transition, no body)")
    public Property close(@PathParam("id") UUID id) {
        return service.close(id);
    }
}
