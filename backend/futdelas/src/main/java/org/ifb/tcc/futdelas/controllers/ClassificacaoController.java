package org.ifb.tcc.futdelas.controllers;

import org.ifb.tcc.futdelas.dtos.ClassificacaoResponse;
import org.ifb.tcc.futdelas.services.ClassificacaoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/classificacao")
@Produces(MediaType.APPLICATION_JSON)
public class ClassificacaoController {

    @Inject
    ClassificacaoService service;

    @GET
    public List<ClassificacaoResponse> buscarClassificacao() {
        return service.buscarClassificacao();
    }
}