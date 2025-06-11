package org.ifb.tcc.futdelas.interfaces.dto.response.campeonato;

import org.ifb.tcc.futdelas.entities.Edicao;
import org.ifb.tcc.futdelas.entities.Fase;
import org.ifb.tcc.futdelas.entities.Rodada;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CampeonatoResponse {
    private String nome;
    private String slug;
    @JsonProperty("nome_popular")
    private String nomePopular;
    @JsonProperty("edicao_atual")
    private Edicao edicaoAtual;
    @JsonProperty("fase_atual")
    private Fase faseAtual;
    @JsonProperty("rodada_atual")
    private Rodada rodadaAtual;
    private String logo;
    private String status;
    private String tipo;
    private String regiao;
}
