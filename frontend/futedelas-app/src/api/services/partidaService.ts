import api from "../http";
import { Partida } from "../interfaces/Partida"
import { PartidaResponse } from "../interfaces/PartidaResponse";

export async function getPartidasDetalhes(): Promise<PartidaResponse> {
  const response = await api.get('/partida');
  return response.data;
}
