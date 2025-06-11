package org.ifb.tcc.futdelas.resources;

import java.util.List;

import org.ifb.tcc.futdelas.entities.Time;
import org.ifb.tcc.futdelas.services.TimeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeResource {

    @Inject
    TimeService service;

    @GET
    public List<Time> buscarTimesCampeonato() {
        return service.buscarTimesCampeonato();
    }

    @POST
    @Path("/favorito")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarTimeFavorito(
            @Valid UsuarioTimeRequest usuarioTimeRequest) {
        try {
            TimeFavorito timeFavorito = service.registrarTimeFavorito(usuarioTimeRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(new TimeFavoritoResponse(timeFavorito))
                    .build();

        } catch (FirebaseAuthException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token inválido ou expirado"))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao processar a requisição"))
                    .build();
        }
    }

}

