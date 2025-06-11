import api from "../http";
import { UsuarioNotificacao } from "../interfaces/UsuarioNotificacao";

export async function registrarNotificacao(usuarioNotificacao: UsuarioNotificacao): Promise<any> {
  try {
    const response = await api.post('/notificacao/registrar', usuarioNotificacao);
    return response;
  } catch (error) {
    throw error;
  }
}


export async function atualizarNotificacao(usuarioNotificacao: UsuarioNotificacao): Promise<any> {
  try {
    const response = await api.post('/notificacao/atualizar', usuarioNotificacao);
    return response;
  } catch (error) {
    throw error;
  }
}