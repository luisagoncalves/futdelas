<template>
    <ion-segment-content id="first">
      <ion-grid>
        <CompetitionCard 
          :title="competition.title" 
          :teamName="competition.teamName" 
          :teamLogo="competition.teamLogo"
          :teamsCount="competition.teamsCount" 
          :roundsInfo="competition.roundsInfo" />

        <NextMatchCard 
          :title="nextMatch.title" 
          :homeTeamName="nextMatch.homeTeamName" 
          :homeTeamLogo="nextMatch.homeTeamLogo"
          :awayTeamName="nextMatch.awayTeamName" 
          :awayTeamLogo="nextMatch.awayTeamLogo" 
          :matchDateTime="nextMatch.matchDateTime"
          :icon="shareSocial" />

        <RankingTable />
      </ion-grid>
    </ion-segment-content>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CompetitionCard from '@/components/cards/CompetitionCard.vue'
import NextMatchCard from '@/components/cards/NextMatchCard.vue'
import RankingTable from '@/components/RankingTable.vue'
import { shareSocial } from 'ionicons/icons'

const competition = ref({
  title: "Brasileirão Feminino",
  teamName: "Corinthians",
  teamLogo: "src/assets/images/corinthians.png",
  teamsCount: "16 times",
  roundsInfo: "15 rodadas"
})

const nextMatch = ref({
  title: "Próxima partida",
  homeTeamName: "Cruzeiro",
  homeTeamLogo: "src/assets/images/cruzeiro.png",
  awayTeamName: "Santos",
  awayTeamLogo: "src/assets/images/santos.png",
  matchDateTime: "16 de mai., 19:00"
})
</script>