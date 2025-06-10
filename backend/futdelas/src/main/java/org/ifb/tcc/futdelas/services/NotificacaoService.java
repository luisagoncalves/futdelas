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

    public String enviarNotificacaoDispositivo(String dispositivoToken, String titulo, String corpo)
            throws FirebaseMessagingException {

        Message notificacao = Message.builder()
                .setToken(dispositivoToken)
                .setNotification(Notification.builder()
                        .setTitle(titulo)
                        .setBody(corpo)
                        .build())
                .build();

        return FirebaseMessaging.getInstance().send(notificacao);
    }
}
