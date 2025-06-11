package org.ifb.tcc.futdelas.interfaces.dto.request.notificacao;

import lombok.Getter;

@Getter
public class NotificacaoRequest {
    private String deviceToken;
    private String title;
    private String body;
}
