package org.ifb.tcc.futdelas.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

import org.ifb.tcc.futdelas.dtos.CampeonatoResponse;

@Getter
@Setter
public class PartidaTimeFavorito {
    @JsonProperty("partida_id")
    private Long partidaId;

    private CampeonatoResponse campeonatoId;

    private String placar;

    private Time timeMandanteId;

    private Time timeVisitanteId;

    @JsonProperty("placar_mandante")
    private Integer placarMandante;

    @JsonProperty("placar_visitante")
    private Integer placarVisitante;

    @JsonProperty("disputa_penalti")
    private Boolean disputaPenalti;

    private String status;

    private String slug;

    @JsonProperty("data_realizacao_iso")
    private void setDataHoraRealizacao(String dataHoraIso) {
        this.dataHoraRealizacao = LocalDateTime.parse(dataHoraIso.replace("-0300", ""), java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Transient
    private LocalDateTime dataHoraRealizacao;

    @JsonProperty("estadio")
    private void unpackEstadioId(Map<String, Object> estadio) {
        this.estadioId = (Integer) estadio.get("estadio_id");
    }
    @Transient
    private Integer estadioId;
}
