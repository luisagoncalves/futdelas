package org.ifb.tcc.futdelas.dtos;

import org.ifb.tcc.futdelas.entities.Edicao;
import org.ifb.tcc.futdelas.entities.Fase;
import org.ifb.tcc.futdelas.entities.Rodada;

public class CampeonatoResponse {
    public String nome;
    public String slug;
    public String nome_popular;
    public Edicao edicao_atual;
    public Fase fase_atual;
    public Rodada rodada;
    public String logo;
    public String status;
    public String tipo;
    public String regiao;
}
