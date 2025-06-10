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

    @Inject
    UsuarioNotificacaoRepository usuarioNotificacaoRepository;

    @Inject
    NotificacaoService notificacaoService;

    @Inject
    PartidaService partidaService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void notificarProximasPartidas() {
        List<UsuarioNotificacao> usuarios = usuarioNotificacaoRepository.buscarAtivos();

        for (UsuarioNotificacao usuario : usuarios) {
            try {
                System.out.println("USUARIO:  " + usuario.getDeviceToken());
                PartidaTimeFavoritoResponse response = partidaService.buscarPartidasPorTime(usuario.getTimeId());

                List<PartidaTimeFavorito> partidas = response.getCampeonatoBrasileiroFeminino();

                if (partidas == null || partidas.isEmpty())
                    continue;

                PartidaTimeFavorito proxima = partidas.stream()
                        .filter(p -> p.getDataHoraRealizacao().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(PartidaTimeFavorito::getDataHoraRealizacao))
                        .findFirst()
                        .orElse(null);

                if (proxima == null)
                    continue;

                if (usuario.getUltimaNotificacaoEnviada() != null &&
                        usuario.getUltimaNotificacaoEnviada().isAfter(LocalDateTime.now().minusMinutes(59))) {
                    continue;
                }

                String titulo = "⚽ Próxima partida do seu time!";
                String corpo = String.format("%s x %s - %s",
                        "fla",
                        "cru",
                        proxima.getDataHoraRealizacao().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));

                notificacaoService.enviarNotificacaoDispositivo(usuario.getDeviceToken(), titulo, corpo);

                usuario.setUltimaNotificacaoEnviada(LocalDateTime.now());
                usuarioNotificacaoRepository.persist(usuario);

            } catch (Exception e) {
                LOGGER.error("Erro ao enviar notificação para deviceToken: " + usuario.getDeviceToken(), e);
            }
        }
    }

}
