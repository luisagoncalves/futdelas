package org.ifb.tcc.futdelas.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ifb.tcc.futdelas.client.ApiFutebolClient;
import org.ifb.tcc.futdelas.dtos.PartidaResponse;
import org.ifb.tcc.futdelas.entities.Partida;
import org.ifb.tcc.futdelas.dtos.PartidaTimeFavoritoResponse;

@ApplicationScoped
public class PartidaService {

    @Inject
    @RestClient
    ApiFutebolClient apiFutebolClient;

    @ConfigProperty(name = "api-futebol.token")
    String token;

    private String obterCabecalhoAutorizacao() {
        return "Bearer " + token;
    }

    public PartidaResponse buscarPartidas() {
        return apiFutebolClient.buscarPartidasPorCampeonatoId(obterCabecalhoAutorizacao());
    }

    public Partida buscarPartidaPorId(Long id) {
        return apiFutebolClient.buscarPartidaPorId(obterCabecalhoAutorizacao(), id);
    }

    public PartidaTimeFavoritoResponse buscarPartidasPorTime(Integer timeId) {
        return apiFutebolClient.buscarPartidasPorTimeId(obterCabecalhoAutorizacao(), timeId);
    }
}