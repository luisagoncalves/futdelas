package org.ifb.tcc.futdelas.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacaoRequest {
    private String tokenDispositivo;
    private String userId;
    private Integer timeFavoritoId;
    private boolean status;
}
