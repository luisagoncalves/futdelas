import api from "../http";
import { Time } from "../interfaces/Time";

export async function getTimes(): Promise<Time[]> {
  const response = await api.get('/time');
  return response.data;
}

export async function buscarTimeFavorito(idUsuario: number): Promise<Time> {
  const response = await api.get(`/favorito/${idUsuario}`);
  return response.data;
}
