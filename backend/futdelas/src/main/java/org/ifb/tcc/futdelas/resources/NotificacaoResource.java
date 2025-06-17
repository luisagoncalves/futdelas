package org.ifb.tcc.futdelas.resources;

import org.ifb.tcc.futdelas.dtos.request.NotificacaoRequest;
import org.ifb.tcc.futdelas.services.NotificacaoService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/notificacao")
public class NotificacaoResource {

    @Inject
    NotificacaoService service;

    @POST
    @Transactional
    public Response registrar(NotificacaoRequest request) {
        service.cadastrarNotificacao(request);
        return Response
            .status(Response.Status.CREATED)
            .build();
    }
}
