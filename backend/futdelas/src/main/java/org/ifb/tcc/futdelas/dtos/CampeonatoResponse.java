package org.ifb.tcc.futdelas.dtos;

import org.ifb.tcc.futdelas.entities.Edicao;
import org.ifb.tcc.futdelas.entities.Fase;
import org.ifb.tcc.futdelas.entities.Rodada;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CampeonatoResponse {
    public String nome;
    public String slug;
    @JsonProperty("nome_popular")
    public String nomePopular;
    @JsonProperty("edicao_atual")
    public Edicao edicaoAtual;
    @JsonProperty("fase_atual")
    public Fase faseAtual;
    @JsonProperty("rodada_atual")
    public Rodada rodadaAtual;
    public String logo;
    public String status;
    public String tipo;
    public String regiao;
}
