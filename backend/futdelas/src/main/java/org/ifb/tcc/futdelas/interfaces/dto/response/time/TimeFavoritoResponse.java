package org.ifb.tcc.futdelas.interfaces.dto.request.time;

import lombok.Getter;
import java.time.LocalDateTime;

public class TimeFavoritoResponse {
    private Long id;
    private String usuarioId;
    private Long timeId;
    private LocalDateTime dataAdicao;
}