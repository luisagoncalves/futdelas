package org.ifb.tcc.futdelas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ifb.tcc.futdelas.client.ApiFutebolClient;
import org.ifb.tcc.futdelas.dtos.ClassificacaoResponse;
import org.ifb.tcc.futdelas.entities.Time;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TimeService {

    @Inject
    @RestClient
    ApiFutebolClient apiFutebolClient;

    @ConfigProperty(name = "api-futebol.token")
    private String token;

    public List<Time> buscarTimesCampeonato() {
        String authorizationHeader = "Bearer " + token;

        List<ClassificacaoResponse> classificacaoResponse = apiFutebolClient.buscarClassificacaoPorCampeonatoId(authorizationHeader);

        List<Time> times = classificacaoResponse.stream()
            .map(ClassificacaoResponse::getTime)
            .distinct()
            .collect(Collectors.toList());


        return times;
    }
}

