import api from "../http";

export async function getPartidas(): Promise<any> {
  const response = await api.get('/partida');
  return response.data;
}
