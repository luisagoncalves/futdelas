package org.ifb.tcc.futdelas.interfaces.dto.response.partida;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import org.ifb.tcc.futdelas.domain.entities.PartidaTimeFavorito;

@Getter
public class PartidaTimeFavoritoResponse {
    @JsonProperty("campeonato-brasileiro-feminino")
    private List<PartidaTimeFavorito> partidasTimeFavorito;
}
