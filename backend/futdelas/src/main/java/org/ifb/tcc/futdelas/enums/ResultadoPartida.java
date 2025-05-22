package org.ifb.tcc.futdelas.enums;


public enum ResultadoPartida {
    VITORIA("v"),
    EMPATE("e"),
    DERROTA("d");

    private final String codigo;

    ResultadoPartida(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static ResultadoPartida fromCodigo(String codigo) {
        for (ResultadoPartida resultado : values()) {
            if (resultado.getCodigo().equalsIgnoreCase(codigo)) {
                return resultado;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
