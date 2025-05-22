package org.ifb.tcc.futdelas.entities;

public class Fase {
    private int fase_id;
    private String nome;
    private String slug;
    private String tipo;

    public int getFaseId(){
        return fase_id;
    }
    public String getNome(){
        return nome;
    }
    public String getSlug(){
        return slug;
    }
    public String getTipo(){
        return tipo;
    }
}