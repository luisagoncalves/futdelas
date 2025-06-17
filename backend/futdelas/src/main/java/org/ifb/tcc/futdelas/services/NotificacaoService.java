package org.ifb.tcc.futdelas.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;

import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.ifb.tcc.futdelas.dtos.request.NotificacaoRequest;
import org.ifb.tcc.futdelas.dtos.response.NotificacaoResponse;
import org.ifb.tcc.futdelas.entities.Notificacao;
import org.ifb.tcc.futdelas.repositories.NotificacaoRepository;

@ApplicationScoped
public class NotificacaoService {
    private static final Logger LOGGER = Logger.getLogger(NotificacaoService.class.getName());

    @Inject
    NotificacaoRepository repository;

    @PostConstruct
    public void inicializar() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                InputStream serviceAccount = getClass()
                        .getClassLoader()
                        .getResourceAsStream("firebase/serviceAccountKey.json");

                if (serviceAccount == null) {
                    throw new RuntimeException("Arquivo serviceAccountKey.json não encontrado!");
                }

                FirebaseOptions opcoes = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(opcoes);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao inicializar Firebase: " + e.getMessage(), e);
            }
        }
    }

    public NotificacaoResponse cadastrarNotificacao(NotificacaoRequest request) {
        Notificacao notificacao = Notificacao.builder()
                .tokenDispositivo(request.getTokenDispositivo())
                .userId(request.getUserId())
                .timeFavoritoId(request.getTimeFavoritoId())
                .status(request.isStatus())
                .ultimaNotificacao(LocalDateTime.now())
                .build();

        repository.persist(notificacao);

        return NotificacaoResponse.builder()
                .id(notificacao.getId())
                .tokenDispositivo(notificacao.getTokenDispositivo())
                .userId(notificacao.getUserId())
                .timeFavoritoId(notificacao.getTimeFavoritoId())
                .status(notificacao.isStatus())
                .ultimaNotificacao(notificacao.getUltimaNotificacao())
                .build();
    }

    public BatchResponse enviarNotificacoesEmLote(List<NotificacaoRequest> requests)
            throws FirebaseMessagingException {

        List<String> tokens = requests.stream()
                .map(NotificacaoRequest::getTokenDispositivo)
                .collect(Collectors.toList());

        MulticastMessage mensagem = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(Notification.builder()
                        .setTitle("Notificação")
                        .setBody("Corpo da notificação")
                        .build())
                .build();

        return FirebaseMessaging.getInstance().sendEachForMulticast(mensagem);
    }

    @Scheduled(every = "5m") // Executa a cada 1 minuto
    public void dispararNotificacoesAgendadas() {
        try {
            LOGGER.info("Iniciando disparo de notificações agendadas...");

            // Busca notificações pendentes (adicione sua lógica de filtro se necessário)
            List<Notificacao> notificacoesPendentes = repository.listAll();

            if (notificacoesPendentes.isEmpty()) {
                LOGGER.info("Nenhuma notificação pendente para enviar.");
                return;
            }

            // Converte para FCM MulticastMessage
            List<String> tokens = notificacoesPendentes.stream()
                    .map(Notificacao::getTokenDispositivo)
                    .toList();

            MulticastMessage mensagem = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(Notification.builder()
                            .setTitle("Notificação Automática")
                            .setBody("Esta é uma notificação agendada")
                            .build())
                    .build();

            // Envia as notificações
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(mensagem);

            // Atualiza o status/lastSent das notificações
            atualizarNotificacoesEnviadas(notificacoesPendentes, response);

            LOGGER.info("Notificações enviadas: " + response.getSuccessCount() +
                    ", Falhas: " + response.getFailureCount());

        } catch (FirebaseMessagingException e) {
            LOGGER.log(Level.SEVERE, "Erro ao enviar notificações: " + e.getMessage(), e);
        }
    }

    private void atualizarNotificacoesEnviadas(List<Notificacao> notificacoes, BatchResponse response) {
        List<SendResponse> responses = response.getResponses();

        for (int i = 0; i < notificacoes.size(); i++) {
            Notificacao notificacao = notificacoes.get(i);
            SendResponse sendResponse = responses.get(i);

            if (sendResponse.isSuccessful()) {
                notificacao.setUltimaNotificacao(LocalDateTime.now());
                // Adicione outros campos de status se necessário
            } else {
                LOGGER.warning("Falha ao enviar para token " + notificacao.getTokenDispositivo() +
                        ": " + sendResponse.getException().getMessage());
            }
        }

        repository.persist(notificacoes); // Atualiza em batch
    }
}
