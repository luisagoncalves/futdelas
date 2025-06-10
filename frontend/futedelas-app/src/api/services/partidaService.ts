import api from "../http";
import { Partida } from "../interfaces/Partida"
import { PartidaResponse } from "../interfaces/PartidaResponse";

export async function getPartidasDetalhes(): Promise<PartidaResponse> {
  const response = await api.get('/partida');
  return response.data;
}

export async function buscarImagemPartida(id: number) {
    try {
      const response = await api.get(`partida/${id}/image`, {
        responseType: 'blob'
      });
      return response.data;
    } catch (error) {
      console.error('Erro ao obter imagem da partida:', error);
      throw error;
    }
  }