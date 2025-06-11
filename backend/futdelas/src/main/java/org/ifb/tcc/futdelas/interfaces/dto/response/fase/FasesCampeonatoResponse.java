package org.ifb.tcc.futdelas.interfaces.dto.response.fase;

import java.util.List;
import java.util.Map;

import org.ifb.tcc.futdelas.entities.Partida;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class FasesCampeonatoResponse {
    @JsonProperty("quartas-de-final")
    private List<Partida> quartasDefinal;
    @JsonProperty("semifinal")
    private List<Partida> semifinal;
    @JsonProperty("final")
    private List<Partida> faseFinal;
    @JsonProperty("primeira-fase")
    private Map<String, List<Partida>>  primeiraFase;
}
