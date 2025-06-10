<template>
  <ion-segment-content id="second">
    <PartidaCard v-for="(partida, index) in partidas" 
      :key="index" 
      :data="partida.data" 
      :timeMandante="partida.timeMandante"
      :timeMandanteEscudo="partida.timeMandanteEscudo" 
      :timeVisitante="partida.timeVisitante" 
      :timeVisitanteEscudo="partida.timeVisitanteEscudo"
      :horario="partida.horario" 
      :placar="partida.placar" 
      :partidaId="partida.id"
      :icone="shareSocial" />
  </ion-segment-content>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getPartidasDetalhes } from '@/api/services/partidaService';
import { shareSocial } from 'ionicons/icons';
import PartidaCard from '@/components/cards/PartidaCard.vue';

const partidas = ref<Array<{
  data: string;
  timeMandante: string;
  timeMandanteEscudo: string;
  timeVisitante: string;
  timeVisitanteEscudo: string;
  time: string;
  placar: string;
  id: number;
}>>([]);

onMounted(async () => {
  const response = await getPartidasDetalhes();
  console.log('##### partidas', response)
  const _partidas = {
    ...response.partidas,
    primeira_fase: response.partidas['primeira-fase']
  };

  const partidasPrimeiraFase = _partidas.primeira_fase;

  const partidasFormatadas = Object.entries(partidasPrimeiraFase).flatMap(
    ([rodada, partidasRodada]) => {
      return partidasRodada.map((partida) => ({
        id: partida.partida_id,
        data: formatarDataAbreviada(partida.data_realizacao),
        timeMandante: partida.time_mandante.nome_popular,
        timeMandanteEscudo: partida.time_mandante.escudo,
        timeVisitante: partida.time_visitante.nome_popular,
        timeVisitanteEscudo: partida.time_visitante.escudo,
        horario: partida.hora_realizacao,
        placar: partida.status === 'encerrado' ? `${partida.placar_mandante} - ${partida.placar_visitante}` : 'x'
      }));
    }
  );

  partidas.value = partidasFormatadas;
});

function formatarDataAbreviada(dataString: string): string {
  const [dia, mes, ano] = dataString.split('/').map(Number);
  const data = new Date(ano, mes - 1, dia);

  const formatado = new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: 'short',
    year: 'numeric'
  }).format(data);

  return formatado;
}
</script>

