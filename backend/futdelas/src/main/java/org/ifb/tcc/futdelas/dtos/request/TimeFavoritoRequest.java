package org.ifb.tcc.futdelas.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeFavoritoRequest {
    private String usuarioUid;
    private Integer timeId;
}
