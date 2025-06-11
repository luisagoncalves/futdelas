import api from "../http";
import { Campeonato } from "../interfaces/Campeonato";

export async function buscarCampeonato(): Promise<Campeonato> {
  const response = await api.get('/campeonato/info');
  return response.data;
}
