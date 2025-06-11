package org.ifb.tcc.futdelas.interfaces.dto.request.notificacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioNotificacaoRequest {
    private String deviceToken;
    private Integer timeId;
    private boolean ativo;
}
