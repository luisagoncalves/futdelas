package org.ifb.tcc.futdelas.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Edicao {
    @JsonProperty("id_edicao")
    private int idEdicao;
    private String temporada;
    private String nome;
    @JsonProperty("nome_popular")
    private String nomePopular;
    private String slug;
}