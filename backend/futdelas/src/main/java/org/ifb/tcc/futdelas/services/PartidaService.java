package org.ifb.tcc.futdelas.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ifb.tcc.futdelas.client.ApiFutebolClient;
import org.ifb.tcc.futdelas.dtos.PartidaResponse;
import org.ifb.tcc.futdelas.entities.Partida;

@ApplicationScoped
public class PartidaService {

    @Inject
    @RestClient
    ApiFutebolClient apiFutebolClient;

    @ConfigProperty(name = "api-futebol.token")
    String token;

    public PartidaResponse buscarPartidas() {
        String authorizationHeader = "Bearer " + token;
        return apiFutebolClient.buscarPartidasPorCampeonatoId(authorizationHeader);
    }

    public Partida buscarPartidaPorId(Long id) {
        String authorizationHeader = "Bearer " + token;
        return apiFutebolClient.buscarPartidaPorId(authorizationHeader, id);
    }
}
