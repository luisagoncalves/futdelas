package org.ifb.tcc.futdelas.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ifb.tcc.futdelas.client.ApiFutebolClient;
import org.ifb.tcc.futdelas.dtos.CampeonatoResponse;

@ApplicationScoped
public class CampeonatoService {

    @Inject
    @RestClient
    ApiFutebolClient apiFutebolClient;

    @ConfigProperty(name = "api-futebol.token")
    String token;

    private String obterCabecalhoAutorizacao() {
        return "Bearer " + token;
    }

    public CampeonatoResponse buscarInfosCampeonato() {
        try {
            return apiFutebolClient.buscarCampeonatoPorId(obterCabecalhoAutorizacao());
        } catch (Exception e) {
            String mensagemErro = String.format("[CampeonatoService:buscarInfosCampeonato] Erro ao buscar informações do campeonato: %s", e.getMessage());
            LOGGER.error(mensagemErro, e);
            throw new RuntimeException("Falha ao consultar dados do campeonato. Tente novamente mais tarde.", e);
        }
    }
}
