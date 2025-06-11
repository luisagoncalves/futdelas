package org.ifb.tcc.futdelas.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UsuarioNotificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String deviceToken;

    private Integer timeId;

    private boolean notificacoesAtivas;

    private LocalDateTime ultimaNotificacaoEnviada;
}

