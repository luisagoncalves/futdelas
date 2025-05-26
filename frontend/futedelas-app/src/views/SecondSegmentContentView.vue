<template>
  <ion-segment-content id="second">
    <MatchCard
      v-for="(match, index) in matches"
      :key="index"
      :title="match.date"
      :homeTeamName="match.homeTeamName"
      :homeTeamLogo="match.homeTeamLogo"
      :awayTeamName="match.awayTeamName"
      :awayTeamLogo="match.awayTeamLogo"
      :matchDateTime="match.time"
      :icon="shareSocial"
      :placar="match.score"
    />
  </ion-segment-content>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getPartidas } from '@/api/services/partidaService';
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
}>>([]);

onMounted(async () => {
  const response = await getPartidas();
  const partidas = response.partidas.partidas;

  for (const faseKey in partidas) {
    const fase = partidas[faseKey];

    for (const chaveKey in fase) {
      const confronto = fase[chaveKey];

      const processarConfronto = (jogo: any) => {
        matches.value.push({
          date: formatarDataAbreviada(jogo.data_realizacao),
          homeTeamName: jogo.time_mandante.nome_popular,
          homeTeamLogo: jogo.time_mandante.escudo,
          awayTeamName: jogo.time_visitante.nome_popular,
          awayTeamLogo: jogo.time_visitante.escudo,
          time: jogo.hora_realizacao,
          score: '0 X 0' 
        });
      };

      if (confronto.ida) processarConfronto(confronto.ida);
      if (confronto.volta) processarConfronto(confronto.volta);
    }
  }
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
