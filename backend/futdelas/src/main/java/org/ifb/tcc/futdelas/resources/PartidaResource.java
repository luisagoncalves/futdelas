package org.ifb.tcc.futdelas.resources;

import org.jboss.logging.Logger;

import org.ifb.tcc.futdelas.dtos.PartidaResponse;
import org.ifb.tcc.futdelas.entities.Partida;
import org.ifb.tcc.futdelas.dtos.PartidaTimeFavoritoResponse;
import org.ifb.tcc.futdelas.services.ImagemPartidaService;
import org.ifb.tcc.futdelas.services.PartidaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/partida")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PartidaResource {
    private static final Logger LOGGER = Logger.getLogger(PartidaResource.class);

    @Inject
    PartidaService service;

    @Inject
    ImagemPartidaService imagemPartidaService;

    @GET
    public PartidaResponse buscarPartidas() {
        return service.buscarPartidas();
    }

    @GET
    @Path("/{id}")
    public Partida buscarPartidasPorId(@PathParam("id") Long id) {
        return service.buscarPartidaPorId(id);
    }

    @GET
    @Path("/{id}/image")
    @Produces("image/png")
    public Response getMatchImage(@PathParam("id") Long id) throws Exception {
        Partida partida = buscarPartidasPorId(id);
        byte[] imageBytes = imagemPartidaService.generateMatchImage(partida);
        return Response.ok(imageBytes).build();
    }

    @GET
    @Path("/time-favorito/{timeId}")
    public Response buscarPartidasTimeFavorito(@PathParam("timeId") Integer timeId) {
        if (timeId == null) {
            LOGGER.error("[PartidaResource:buscarPartidasTimeFavorito] O id do time é nulo.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parâmetro 'timeId' é obrigatório.")
                    .build();
        }

        try {
            PartidaTimeFavoritoResponse listaPartidas = service.buscarPartidasPorTime(timeId);

            if (listaPartidas == null) { 
                LOGGER.error("[PartidaResource:buscarPartidasTimeFavorito] A lista de partidas está vazia.");
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            LOGGER.info("[PartidaResource:buscarPartidasTimeFavorito] Sucesso ao buscar partidas do time.");
            return Response.ok(listaPartidas).build();

        } catch (Exception e) {
            LOGGER.error("[PartidaResource:buscarPartidasTimeFavorito] Erro ao buscar partidas", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar partidas para o time informado.")
                    .build();
        }
    }
}