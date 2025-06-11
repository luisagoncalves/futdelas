package org.ifb.tcc.futdelas.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class NotificacaoService {

    private static final String ARQUIVO_CREDENCIAIS = "firebase/serviceAccountKey.json";
    private static final String ERRO_ARQUIVO_NAO_ENCONTRADO = "Arquivo serviceAccountKey.json não encontrado!";
    private static final String ERRO_INICIALIZACAO = "Erro ao inicializar Firebase: ";

    @PostConstruct
    public void inicializar() {
        if (FirebaseApp.getApps().isEmpty()) {
            inicializarFirebase();
        }
    }

    private void inicializarFirebase() {
        try (InputStream credenciais = obterCredenciaisFirebase()) {
            FirebaseOptions opcoes = construirOpcoesFirebase(credenciais);
            FirebaseApp.initializeApp(opcoes);
        } catch (IOException e) {
            throw new RuntimeException(ERRO_INICIALIZACAO + e.getMessage(), e);
        }
    }

    private InputStream obterCredenciaisFirebase() {
        InputStream credenciais = getClass().getClassLoader().getResourceAsStream(ARQUIVO_CREDENCIAIS);
        if (credenciais == null) {
            throw new RuntimeException(ERRO_ARQUIVO_NAO_ENCONTRADO);
        }
        return credenciais;
    }

    private FirebaseOptions construirOpcoesFirebase(InputStream credenciais) throws IOException {
        return FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credenciais))
                .build();
    }

    public String enviarNotificacaoDispositivo(String tokenDispositivo, String titulo, String mensagem) 
            throws FirebaseMessagingException {
        
        Message notificacao = construirMensagemNotificacao(tokenDispositivo, titulo, mensagem);
        return FirebaseMessaging.getInstance().send(notificacao);
    }

    private Message construirMensagemNotificacao(String tokenDispositivo, String titulo, String mensagem) {
        return Message.builder()
                .setToken(tokenDispositivo)
                .setNotification(Notification.builder()
                        .setTitle(titulo)
                        .setBody(mensagem)
                        .build())
                .build();
    }
}