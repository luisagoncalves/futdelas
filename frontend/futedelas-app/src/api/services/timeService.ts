import api from "../http";
import { Time } from "../interfaces/Time";
import { AxiosResponse } from "axios";
import { TimeFavorito } from "../interfaces/TimeFavorito";

export async function buscarTimes(): Promise<Time[]> {
  const response = await api.get('/time');
  return response.data;
}

export async function buscarTimeFavorito(usuarioUid: string): Promise<TimeFavorito> {
  const response = await api.get(`/favorito/${usuarioUid}`);
  return response.data;
}

