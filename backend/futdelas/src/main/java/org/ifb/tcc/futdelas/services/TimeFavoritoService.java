package org.ifb.tcc.futdelas.services;

import java.util.Optional;
import java.util.UUID;

import org.ifb.tcc.futdelas.dtos.request.TimeFavoritoRequest;
import org.ifb.tcc.futdelas.dtos.response.TimeFavoritoResponse;
import org.ifb.tcc.futdelas.entities.TimeFavorito;
import org.ifb.tcc.futdelas.repositories.TimeFavoritoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TimeFavoritoService {

    @Inject
    private TimeFavoritoRepository timeFavoritoRepository;

    @Transactional
    public TimeFavoritoResponse cadastrarTimeFavorito(TimeFavoritoRequest timeFavoritoRequest) {
        if (timeFavoritoRequest.getTimeId() == null || timeFavoritoRequest.getUsuarioUid() == null
                || timeFavoritoRequest.getUsuarioUid().isBlank()) {
            throw new IllegalArgumentException("Dados do time favorito inválidos");
        }

        Optional<TimeFavorito> existente = timeFavoritoRepository.buscarPorUsuarioETime(
                timeFavoritoRequest.getUsuarioUid(),
                timeFavoritoRequest.getTimeId());

        if (existente.isPresent()) {
            throw new RuntimeException("Este time já está favoritado pelo usuário");
        }

        TimeFavorito favorito = TimeFavorito.builder()
                .timeId(timeFavoritoRequest.getTimeId())
                .usuarioUid(timeFavoritoRequest.getUsuarioUid())
                .build();

        timeFavoritoRepository.persist(favorito);

        return TimeFavoritoResponse.builder()
                .id(favorito.getId())
                .timeId(favorito.getTimeId())
                .usuarioUid(favorito.getUsuarioUid())
                .dataCriacao(favorito.getDataCriacao())
                .build();
    }

    public TimeFavoritoResponse buscarTimeFavoritoPorUserId(String userId){
        TimeFavorito existente = timeFavoritoRepository.buscarPorUsuario(userId);

        return TimeFavoritoResponse.builder()
                .id(existente.getId())
                .timeId(existente.getTimeId())
                .usuarioUid(userId)
                .dataCriacao(existente.getDataCriacao())
                .build();
    }

    @Transactional
    public void removerTimeFavorito(UUID timeFavoritoId) {
        if (timeFavoritoId == null) {
            throw new IllegalArgumentException("Id inválido.");
        }

        TimeFavorito timeFavorito = TimeFavorito.findById(timeFavoritoId);

        if (timeFavorito == null) {
            throw new EntityNotFoundException("Time favorito não encontrado com ID: " + timeFavoritoId);
        }

        timeFavoritoRepository.delete(timeFavorito);
    }
}
