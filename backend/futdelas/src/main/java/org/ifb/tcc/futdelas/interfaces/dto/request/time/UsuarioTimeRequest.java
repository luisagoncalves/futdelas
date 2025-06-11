package org.ifb.tcc.futdelas.interfaces.dto.request.time;


import jakarta.validation.constraints.NotNull;

public class UsuarioTimeRequest {
    @NotNull(message = "O ID do usuário é obrigatório")
    private String usuarioSessionId;
    @NotNull(message = "O ID do time é obrigatório")
    private Long timeId;
}