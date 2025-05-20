<template>
    <ion-segment-content id="second">
      <MatchCard 
        :title="match.date" 
        :homeTeamName="match.homeTeamName" 
        :homeTeamLogo="match.homeTeamLogo"
        :awayTeamName="match.awayTeamName" 
        :awayTeamLogo="match.awayTeamLogo" 
        :matchDateTime="match.time"
        :icon="shareSocial" 
        :placar="match.score" />
    </ion-segment-content>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import MatchCard from '@/components/cards/MatchCard.vue'
import { shareSocial } from 'ionicons/icons'

const match = ref({
  date: "10 de mai.",
  homeTeamName: "Cruzeiro",
  homeTeamLogo: "src/assets/images/cruzeiro.png",
  awayTeamName: "Santos",
  awayTeamLogo: "src/assets/images/santos.png",
  time: "19:00",
  score: "1 X 0"
})
</script>