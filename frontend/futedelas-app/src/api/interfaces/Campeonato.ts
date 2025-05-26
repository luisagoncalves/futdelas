import { Fase } from "./Fase";
import { Rodada } from "./Rodada";
import { Edicao } from "./Edicao";

class Campeonato {
  nome: string;
  slug: string;
  nome_popular: string;
  edicao_atual: Edicao;
  fase_atual: Fase;
  rodada_atual: Rodada;
  logo: string;
  status: string;
  tipo: string;
  regiao: string;

  constructor() {
    this.nome = "";
    this.slug = "";
    this.nome_popular = "";
    this.edicao_atual = {
      id_edicao: 0,
      temporada: "",
      nome: "",
      nome_popular: "",
      slug: ""
    };
    this.fase_atual = {
      fase_id: 0,
      nome: "",
      slug: "",
      tipo: ""
    };
    this.rodada_atual = {
      nome: "",
      slug: "",
      status: "",
      rodada: 0
    };
    this.logo = "";
    this.status = "";
    this.tipo = "";
    this.regiao = "";
  }
}

export { Campeonato };
