package org.ifb.tcc.futdelas.domain.entities;
import lombok.Data;

@Data
public class Rodada {
    private String nome;
    private String slug;
    private int rodada;
    private String status;
}