import { AxiosResponse } from "axios";
import api from "../http";
import { TimeFavorito } from "../interfaces/TimeFavorito";

export async function registrarTimeFavorito(timeId: number, usuarioUid: string): Promise<any> {
  const timeFavorito = {
    timeId: timeId,
    usuarioUid: usuarioUid
  }
  const response: TimeFavorito = await api.post('/time-favorito', timeFavorito);
  return response;
}

export async function removerTimeFavorito(timeFavoritoId: number): Promise<AxiosResponse> {
  const response = await api.delete(`/time-favorito/${timeFavoritoId}`);
  return response;
}

export async function buscarTimeFavorito(userId: string): Promise<any> {
  const response = await api.get(`/time-favorito/usuario/${userId}`);
  return response.data;
}
