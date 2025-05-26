import api from "../http";
import { Time } from "../interfaces/Time";

export async function getTimes(): Promise<Time[]> {
  const response = await api.get('/time');
  return response.data;
}
