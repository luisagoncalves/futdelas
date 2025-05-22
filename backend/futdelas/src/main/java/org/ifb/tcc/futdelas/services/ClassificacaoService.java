package org.ifb.tcc.futdelas.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ifb.tcc.futdelas.client.ApiFutebolClient;
import org.ifb.tcc.futdelas.dtos.ClassificacaoResponse;

@ApplicationScoped
public class ClassificacaoService {

    @Inject
    @RestClient
    ApiFutebolClient apiFutebolClient;

    @ConfigProperty(name = "api-futebol.token")
    String token;

    public ClassificacaoResponse buscarClassificacao() {
        String authorizationHeader = "Bearer " + token;
        return apiFutebolClient.buscarClassificacaoPorCampeonatoId(authorizationHeader);
    }
}
