package org.ifb.tcc.futdelas.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class NotificationService {

    @PostConstruct
    public void initialize() {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                InputStream serviceAccount = getClass()
                        .getClassLoader()
                        .getResourceAsStream("firebase/serviceAccountKey.json");

                if (serviceAccount == null) {
                    throw new RuntimeException("Arquivo serviceAccountKey.json não encontrado!");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado com sucesso.");
            } catch (IOException e) {
                throw new RuntimeException("Erro ao inicializar Firebase: " + e.getMessage(), e);
            }
        }
    }

    public String sendNotificationToDevice(String deviceToken, String title, String body)
            throws FirebaseMessagingException {

        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }

    public BatchResponse sendNotificationToDevices(List<String> deviceTokens, String title, String body)
            throws FirebaseMessagingException {

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(deviceTokens)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        return FirebaseMessaging.getInstance().sendEachForMulticast(message);
    }
}
