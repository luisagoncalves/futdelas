import api from "../http";
import { Classificacao } from "../interfaces/Classificacao";

export async function getClassificacao(): Promise<Classificacao[]> {
  const response = await api.get('/classificacao');
  return response.data;
}
