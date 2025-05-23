package org.ifb.tcc.futdelas.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {

    @JsonProperty("time_id")
    private Integer timeId;
    @JsonProperty("nome_popular")
    private String nomePopular;
    private String sigla;
    private String escudo;
}
