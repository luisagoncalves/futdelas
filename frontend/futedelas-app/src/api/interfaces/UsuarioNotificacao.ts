export class UsuarioNotificacao {
  id: number | undefined;
  deviceToken: string;
  timeId: number;
  ativo: boolean;

  constructor(deviceToken: string, timeId: number, ativo: boolean) {
    this.deviceToken = deviceToken;
    this.timeId = timeId;
    this.ativo = ativo;
  }
}