import api from "../http";
import { NotificationRequest } from "../interfaces/Notificacao";

export async function registerTokenOnBackend(request: NotificationRequest): Promise<any> {
  try {
    const response = await api.post('/notifications/device', request);
    return response;
  } catch (error) {
    console.error('Erro ao registrar token no backend:', error);
    throw error;
  }
}