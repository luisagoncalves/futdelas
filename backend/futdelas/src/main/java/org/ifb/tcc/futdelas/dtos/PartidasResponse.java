package org.ifb.tcc.futdelas.dtos;

import java.util.List;
import java.util.Map;

import org.ifb.tcc.futdelas.entities.Partida;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartidasResponse {
    @JsonProperty("quartas-de-final")
    private List<Partida> quartasDefinal;
    private List<Partida> semifinal;
    @JsonProperty("final")
    private List<Partida> faseFinal;
    @JsonProperty("primeira-fase")
    private Map<String, List<Partida>>  primeiraFase;
}
