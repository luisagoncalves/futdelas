package org.ifb.tcc.futdelas.resources;

import org.ifb.tcc.futdelas.dtos.UsuarioNotificacaoDTO;
import org.ifb.tcc.futdelas.entities.UsuarioNotificacao;
import org.ifb.tcc.futdelas.repositories.UsuarioNotificacaoRepository;
import org.ifb.tcc.futdelas.services.NotificacaoAgendadaService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notificacao")
public class NotificacaoResource {

    @Inject
    NotificacaoAgendadaService notificacaoAgendadaService;

    @Inject
    UsuarioNotificacaoRepository usuarioNotificacaoRepository;

    @POST
    @Path("/registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response registrar(UsuarioNotificacaoDTO dto) {
        UsuarioNotificacao usuario = usuarioNotificacaoRepository.find("deviceToken", dto.getDeviceToken())
                .firstResult();
        if (usuario == null) {
            usuario = new UsuarioNotificacao();
        }

        usuario.setDeviceToken(dto.getDeviceToken());
        usuario.setTimeId(dto.getTimeId());
        usuario.setNotificacoesAtivas(dto.isAtivo());
        usuarioNotificacaoRepository.persist(usuario);

        return Response.ok().build();
    }

    @POST
    @Path("/disparar")
    public Response dispararNotificacoesManualmente() {
        try {
            notificacaoAgendadaService.notificarProximasPartidas();
            return Response.ok("Notificações enviadas com sucesso.").build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao disparar notificações: " + e.getMessage()).build();
        }
    }
}
