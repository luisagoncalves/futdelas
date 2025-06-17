package org.ifb.tcc.futdelas.resources;

import java.util.UUID;

import org.ifb.tcc.futdelas.dtos.request.TimeFavoritoRequest;
import org.ifb.tcc.futdelas.dtos.response.TimeFavoritoResponse;
import org.ifb.tcc.futdelas.services.TimeFavoritoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/time-favorito")
public class TimeFavoritoResource {

    @Inject
    private TimeFavoritoService service;

    @POST
    public Response registrarTimeFavorito(TimeFavoritoRequest timeFavoritoRequest) {
        service.cadastrarTimeFavorito(timeFavoritoRequest);
        return Response
            .status(Response.Status.CREATED)
            .build();
    }

    @GET
    @Path("/usuario/{id}")
    public Response buscarTimeFavoritoPorUserId(@PathParam("id") String usuarioUid) {
        TimeFavoritoResponse response = service.buscarTimeFavoritoPorUserId(usuarioUid);
        return Response
                .status(Response.Status.OK)
                .entity(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletarTimeFavorito(@PathParam("id") UUID timeFavoritoId) {
        service.removerTimeFavorito(timeFavoritoId);
        return Response
            .status(Response.Status.NO_CONTENT)
            .build();
    }
}
