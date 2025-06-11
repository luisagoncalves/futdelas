package org.ifb.tcc.futdelas.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Partida {
        @JsonProperty("partida_id")
        private Long partidaId;
        @JsonProperty("time_mandante")
        private Time timeMandante;
        @JsonProperty("time_visitante")
        private Time timeVisitante;
        private String status;
        private String slug;
        @JsonProperty("data_realizacao")
        private String dataRealizacao;
        @JsonProperty("hora_realizacao")
        private String horaRealizacao;
        @JsonProperty("data_realizacao_iso")
        private String dataRealizacaoIso;
        @JsonProperty("_link")
        private String link;
        private String placar;
        @JsonProperty("placar_mandante")
        private Long placarMandante;
        @JsonProperty("placar_visitante")
        private Long placarVisitante;
    }