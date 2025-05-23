package org.ifb.tcc.futdelas.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Fase {
    @JsonProperty("fase_id")
    private Integer faseId;
    private String nome;
    private String slug;
    private String tipo;
}