package org.ifb.tcc.futdelas.entities;
import lombok.Data;

@Data
public class Rodada {
    private String nome;
    private String slug;
    private int rodada;
    private String status;
}