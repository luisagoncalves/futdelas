package org.ifb.tcc.futdelas.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacaoResponse {
    private UUID id;
    private String tokenDispositivo;
    private String userId;
    private Integer timeFavoritoId;
    private boolean status;
    private LocalDateTime ultimaNotificacao;
}
