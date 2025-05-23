package org.ifb.tcc.futdelas.dtos;

import org.ifb.tcc.futdelas.entities.Time;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassificacaoResponse {
    private Integer posicao;
    private Integer pontos;
    private Time time;
    private Integer jogos;
    private Integer vitorias;
    private Integer empates;
    private Integer derrotas;
    @JsonProperty("gols_pro")
    private Integer golsPro;
    @JsonProperty("gols_contra")
    private Integer golsContra;
    @JsonProperty("saldo_gols")
    private Integer saldoGols;
    private Double aproveitamento;
    @JsonProperty("variacao_posicao")
    private Integer variacaoPosicao;
    @JsonProperty("ultimos_jogos")
    private List<String> ultimosJogos;
}