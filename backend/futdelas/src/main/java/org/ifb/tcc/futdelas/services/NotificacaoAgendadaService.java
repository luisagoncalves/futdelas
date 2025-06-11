package org.ifb.tcc.futdelas.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import org.ifb.tcc.futdelas.dtos.PartidaTimeFavoritoResponse;
import org.ifb.tcc.futdelas.entities.PartidaTimeFavorito;
import org.ifb.tcc.futdelas.entities.UsuarioNotificacao;
import org.ifb.tcc.futdelas.repositories.UsuarioNotificacaoRepository;
import org.jboss.logging.Logger;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NotificacaoAgendadaService {
    private static final Logger LOGGER = Logger.getLogger(NotificacaoAgendadaService.class);
    private static final String FORMATO_DATA_HORA = "dd/MM HH:mm";
    private static final String TITULO_NOTIFICACAO = "⚽ Próxima partida do seu time!";
    private static final int INTERVALO_MINUTOS_NOTIFICACAO = 59;

    @Inject
    UsuarioNotificacaoRepository usuarioNotificacaoRepository;

    @Inject
    NotificacaoService notificacaoService;

    @Inject
    PartidaService partidaService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void notificarProximasPartidas() {
        List<UsuarioNotificacao> usuarios = usuarioNotificacaoRepository.buscarAtivos();
        
        usuarios.forEach(this::processarNotificacaoUsuario);
    }

    private void processarNotificacaoUsuario(UsuarioNotificacao usuario) {
        try {
            PartidaTimeFavoritoResponse response = partidaService.buscarPartidasPorTime(usuario.getTimeId());
            PartidaTimeFavorito proximaPartida = encontrarProximaPartida(response);

            if (proximaPartida == null || devePularNotificacao(usuario)) {
                return;
            }

            enviarNotificacao(usuario, proximaPartida);
            atualizarUltimaNotificacao(usuario);

        } catch (Exception e) {
            LOGGER.error("Erro ao enviar notificação para deviceToken: " + usuario.getDeviceToken(), e);
        }
    }

    private PartidaTimeFavorito encontrarProximaPartida(PartidaTimeFavoritoResponse response) {
        List<PartidaTimeFavorito> partidas = response.getCampeonatoBrasileiroFeminino();
        
        if (partidas == null || partidas.isEmpty()) {
            return null;
        }

        return partidas.stream()
                .filter(p -> p.getDataHoraRealizacao().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(PartidaTimeFavorito::getDataHoraRealizacao))
                .orElse(null);
    }

    private boolean devePularNotificacao(UsuarioNotificacao usuario) {
        return usuario.getUltimaNotificacaoEnviada() != null &&
               usuario.getUltimaNotificacaoEnviada().isAfter(LocalDateTime.now().minusMinutes(INTERVALO_MINUTOS_NOTIFICACAO));
    }

    private void enviarNotificacao(UsuarioNotificacao usuario, PartidaTimeFavorito partida) {
        String mensagem = String.format("%s x %s - %s",
                "fla",
                "cru",
                partida.getDataHoraRealizacao().format(DateTimeFormatter.ofPattern(FORMATO_DATA_HORA)));

        notificacaoService.enviarNotificacaoDispositivo(usuario.getDeviceToken(), TITULO_NOTIFICACAO, mensagem);
    }

    private void atualizarUltimaNotificacao(UsuarioNotificacao usuario) {
        usuario.setUltimaNotificacaoEnviada(LocalDateTime.now());
        usuarioNotificacaoRepository.persist(usuario);
    }
}