package org.ifb.tcc.futdelas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartidaResponse {
    private CampeonatoResponse campeonato;
    private PartidasResponse partidas;
}
