package org.ifb.tcc.futdelas.controllers;

import org.ifb.tcc.futdelas.dtos.CampeonatoResponse;
import org.ifb.tcc.futdelas.services.CampeonatoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/campeonato")
@Produces(MediaType.APPLICATION_JSON)
public class CampeonatoController {

    @Inject
    CampeonatoService service;

    @GET
    @Path("/info")
    public CampeonatoResponse buscarCampeonato() {
        return service.buscarInfosCampeonato();
    }
}