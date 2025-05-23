package org.ifb.tcc.futdelas.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.Map;

@Data
public class PrimeiraFaseContainer {

    @JsonProperty("primeira-fase")
    private Map<String, Chave> primeiraFase;
}
