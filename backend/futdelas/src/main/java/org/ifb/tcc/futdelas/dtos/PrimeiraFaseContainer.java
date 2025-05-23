package org.ifb.tcc.futdelas.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class PrimeiraFaseContainer {

    @JsonProperty("primeira-fase")
    private Map<String, Chave> primeiraFase;
}
