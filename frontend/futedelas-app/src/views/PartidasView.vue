<template>
  <div class="lista-partidas-container">
    <div v-if="carregando" class="estado-carregando">
      <ion-spinner name="crescent" class="spinner-carregamento"></ion-spinner>
      <p>Carregando partidas...</p>
    </div>

    <div v-else-if="listaPartidas.length === 0" class="estado-erro">
      <ion-icon :icon="alert" color="danger" size="large"></ion-icon>
      <ion-text>Erro ao carregar partidas</ion-text>
      <ion-button @click="carregarPartidas" fill="clear">
        Tentar novamente
      </ion-button>
    </div>

    <template v-else>
      <div class="lista-partidas">
        <PartidaCard 
          v-for="partida in listaPartidas" 
          :key="partida.id"
          :data-formatada="partida.dataFormatada"
          :nome-time-mandante="partida.mandante.nome"
          :escudo-time-mandante="partida.mandante.escudo"
          :nome-time-visitante="partida.visitante.nome"
          :escudo-time-visitante="partida.visitante.escudo"
          :horario="partida.horario"
          :placar="partida.placar"
          :id-partida="partida.id"
          :icone-compartilhar="iconeCompartilhar"
        />
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { 
  IonSpinner,
  IonIcon
} from '@ionic/vue';
import { shareSocial as iconeCompartilhar, alert } from 'ionicons/icons';
import PartidaCard from '@/components/cards/PartidaCard.vue';
import { buscarDetalhesPartidas } from '@/api/services/partidaService';

interface TimePartida {
  nome: string;
  escudo: string;
}

interface Partida {
  id: number;
  dataFormatada: string;
  mandante: TimePartida;
  visitante: TimePartida;
  horario: string;
  placar: string;
}

const listaPartidas = ref<Partida[]>([]);
const carregando = ref(true);

onMounted(async () => {
  await carregarPartidas();
});

async function carregarPartidas() {
  try {
    carregando.value = true;
    const resposta = await buscarDetalhesPartidas();
    
    listaPartidas.value = formatarDadosPartidas(resposta);
  } catch (erro) {
  } finally {
    carregando.value = false;
  }
}

function formatarDadosPartidas(dados: any): Partida[] {
  const partidasPrimeiraFase = dados.partidas['primeira-fase'] || {};
  
  return Object.entries(partidasPrimeiraFase).flatMap(([rodada, partidasRodada]) => {
    return (partidasRodada as any[]).map(partida => ({
      id: partida.partida_id,
      dataFormatada: formatarDataAbreviada(partida.data_realizacao),
      mandante: {
        nome: partida.time_mandante.nome_popular,
        escudo: partida.time_mandante.escudo
      },
      visitante: {
        nome: partida.time_visitante.nome_popular,
        escudo: partida.time_visitante.escudo
      },
      horario: partida.hora_realizacao,
      placar: partida.status === 'encerrado' 
        ? `${partida.placar_mandante} - ${partida.placar_visitante}` 
        : 'x'
    }));
  });
}

function formatarDataAbreviada(dataString: string): string {
  const [dia, mes, ano] = dataString.split('/').map(Number);
  const data = new Date(ano, mes - 1, dia);

  return new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: 'short',
    year: 'numeric'
  }).format(data)
    .replace(/ de /g, ' ')
    .replace(/\./g, '');
}
</script>

<style scoped>
.lista-partidas-container {
  position: relative;
  min-height: 200px;
}

.lista-partidas {
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 8px;
}

.estado-carregando {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 0;
  gap: 16px;
  color: var(--ion-color-medium);
}

.spinner-carregamento {
  width: 48px;
  height: 48px;
  color: var(--ion-color-primary);
}

.sem-partidas {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  gap: 16px;
  color: var(--ion-color-medium);
  text-align: center;
}

.icone-sem-partidas {
  font-size: 48px;
}

@media (min-width: 768px) {
  .lista-partidas {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 16px;
  }
}

.estado-carregamento,
.estado-erro,
.estado-vazio {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 32px;
  gap: 16px;
  color: var(--ion-color-medium);
}

.estado-carregamento ion-spinner {
  width: 48px;
  height: 48px;
}

.estado-erro {
  color: var(--ion-color-danger);
}

.estado-erro h3 {
  margin: 0;
  font-weight: bold;
}

.estado-vazio ion-icon {
  color: var(--ion-color-medium-shade);
  font-size: 48px;
}
</style>