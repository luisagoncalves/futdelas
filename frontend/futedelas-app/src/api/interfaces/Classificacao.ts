import { Time } from "./Time";

class Classificacao {
  posicao: number;
  pontos: number;
  time: Time;
  jogos: number;
  vitorias: number;
  empates: number;
  derrotas: number;
  gols_pro: number;
  gols_contra: number;
  saldo_gols: number;
  aproveitamento: number;
  variacao_posicao: number;
  ultimos_jogos: string[];

  constructor() {
    this.posicao = 0;
    this.pontos = 0;
    this.time = {
      time_id: 0,
      nome_popular: "",
      sigla: "",
      escudo: "",
    };
    this.jogos = 0;
    this.vitorias = 0;
    this.empates = 0;
    this.derrotas = 0;
    this.gols_pro = 0;
    this.gols_contra = 0;
    this.saldo_gols = 0;
    this.aproveitamento = 0;
    this.variacao_posicao = 0;
    this.ultimos_jogos = [];
  }
}

export { Classificacao };