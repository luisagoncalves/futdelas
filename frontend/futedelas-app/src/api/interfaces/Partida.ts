import { Time } from "./Time";

export interface Partida {
    partida_id: number;
    time_mandante: Time;
    time_visitante: Time;
    status: string;
    data_realizacao: string;
    dataRealizacao: string;
    hora_realizacao: string
    horaRealizacao: string;
    placar: string;
    placar_mandante: string;
    placar_visitante: string;
}