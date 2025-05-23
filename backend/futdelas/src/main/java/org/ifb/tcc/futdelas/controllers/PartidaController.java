package org.ifb.tcc.futdelas.controllers;

import org.ifb.tcc.futdelas.dtos.PartidaResponse;
import org.ifb.tcc.futdelas.services.PartidaService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/classificacao")
@Produces(MediaType.APPLICATION_JSON)
public class PartidaController {

    @Inject
    PartidaService service;

    @GET
    @Path("/partidas")
    public PartidaResponse buscarPartidas() {
        return service.buscarPartidas();
    }
}