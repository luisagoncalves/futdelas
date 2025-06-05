package org.ifb.tcc.futdelas.controllers;

import org.ifb.tcc.futdelas.dtos.PartidaResponse;
import org.ifb.tcc.futdelas.entities.Partida;
import org.ifb.tcc.futdelas.services.PartidaImageService;
import org.ifb.tcc.futdelas.services.PartidaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/partida")
@Produces(MediaType.APPLICATION_JSON)
public class PartidaController {

    @Inject
    PartidaService service;

    @Inject
    PartidaImageService imagemPartidaService;

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
        // 1. Busca os dados da partida (síncrono)
        Partida partida = buscarPartidasPorId(id);

        // 2. Gera a imagem (síncrono)
        byte[] imageBytes = imagemPartidaService.generateMatchImage(partida);

        // 3. Retorna a resposta
        return Response.ok(imageBytes).build();
    }
}