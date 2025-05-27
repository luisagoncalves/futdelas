import { Campeonato } from "./Campeonato";
import { Partida } from "./Partida";

export interface PartidaResponse {
    campeonato: any;
    partidas: PartidasResponse;
}

export interface PartidasResponse {
  ['quartas-de-final']: Partida[];
  ['semifinal']: Partida[];
  ['final']: Partida[];
  ['primeira-fase']: {
    [rodada: string]: Partida[];
  };
}