package org.ifb.tcc.futdelas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioNotificacaoDTO {
    private String deviceToken;
    private Integer timeId;
    private boolean ativo;
}
