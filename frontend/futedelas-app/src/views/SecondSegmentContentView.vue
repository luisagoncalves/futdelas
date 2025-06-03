<template>
  <ion-segment-content id="second">
    <MatchCard v-for="(match, index) in matches" :key="index" :title="match.date" :homeTeamName="match.homeTeamName"
      :homeTeamLogo="match.homeTeamLogo" :awayTeamName="match.awayTeamName" :awayTeamLogo="match.awayTeamLogo"
      :matchDateTime="match.time" :icon="shareSocial" :placar="match.score" 
      :matchId="match.id"  />
  </ion-segment-content>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getPartidasDetalhes } from '@/api/services/partidaService';
import { shareSocial } from 'ionicons/icons';
import MatchCard from '@/components/cards/MatchCard.vue';

const matches = ref<Array<{
  date: string;
  homeTeamName: string;
  homeTeamLogo: string;
  awayTeamName: string;
  awayTeamLogo: string;
  time: string;
  score: string;
  id: number;
}>>([]);

onMounted(async () => {
  const response = await getPartidasDetalhes();

  const partidas = {
    ...response.partidas,
    primeira_fase: response.partidas['primeira-fase']
  };

  const partidasPrimeiraFase = partidas.primeira_fase;

  const partidasFormatadas = Object.entries(partidasPrimeiraFase).flatMap(
    ([rodada, partidasRodada]) => {
      return partidasRodada.map((partida) => ({
        id: partida.partida_id,
        date: formatarDataAbreviada(partida.data_realizacao),
        homeTeamName: partida.time_mandante.nome_popular,
        homeTeamLogo: partida.time_mandante.escudo,
        awayTeamName: partida.time_visitante.nome_popular,
        awayTeamLogo: partida.time_visitante.escudo,
        time: partida.hora_realizacao,
        score: partida.status === 'encerrado' ? `${partida.placar_mandante} - ${partida.placar_visitante}` : 'x'
      }));
    }
  );

  matches.value = partidasFormatadas;
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

