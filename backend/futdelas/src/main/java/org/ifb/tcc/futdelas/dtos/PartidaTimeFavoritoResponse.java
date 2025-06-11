package org.ifb.tcc.futdelas.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import org.ifb.tcc.futdelas.entities.PartidaTimeFavorito;

@Getter
@Setter
public class PartidaTimeFavoritoResponse {
    @JsonProperty("campeonato-brasileiro-feminino")
    private List<PartidaTimeFavorito> campeonatoBrasileiroFeminino;
}
