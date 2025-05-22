package org.ifb.tcc.futdelas.entities;

import org.ifb.tcc.futdelas.entities.Time;
import org.ifb.tcc.futdelas.enums.ResultadoPartida;
import java.util.List;

public class TabelaClassificacao {
    public int posicao;
    public int pontos;
    public Time time;
    public int jogos;
    public int vitorias;
    public int empates;
    public int derrotas;
    public int gols_pro;
    public int gols_contra;
    public int saldo_gols;
    public int aproveitamento;
    public int variacao_posicao;
    public List<ResultadoPartida> ultimos_jogos;
}