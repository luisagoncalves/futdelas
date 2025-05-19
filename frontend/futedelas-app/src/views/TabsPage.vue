<template>
  <AppHeader />
  <AppSegment />

  <ion-segment-view>
    <ion-segment-content id="first">
      <ion-grid>
        <CompetitionCard title="Brasileirão Feminino" teamName="Corinthians" teamLogo="src/assets/images/corinthians.png"
          teamsCount="16 times" roundsInfo="15 rodadas" />

        <NextMatchCard title="Próxima partida" homeTeamName="Cruzeiro" homeTeamLogo="src/assets/images/cruzeiro.png"
          awayTeamName="Santos" awayTeamLogo="src/assets/images/santos.png" matchDateTime="16 de mai., 19:00"
          :icon="shareSocial" />

        <RankingTable />
      </ion-grid>
    </ion-segment-content>

    <ion-segment-content id="second">
      <MatchCard title="10 de mai." homeTeamName="Cruzeiro" homeTeamLogo="src/assets/images/cruzeiro.png"
        awayTeamName="Santos" awayTeamLogo="src/assets/images/santos.png" matchDateTime="19:00"
        :icon="shareSocial" placar="1 X 0" />
    </ion-segment-content>

    <ion-segment-content id="third">
      <TeamCard team-name="Santos" team-logo="src/assets/images/santos.png" :is-initially-favorite="false" />
    </ion-segment-content>
  </ion-segment-view>
</template>


<script setup lang="ts">
import AppHeader from '@/components/layout/AppHeader.vue'
import AppSegment from '@/components/layout/AppSegment.vue'

import CompetitionCard from '@/components/cards/CompetitionCard.vue'
import NextMatchCard from '@/components/cards/NextMatchCard.vue'
import MatchCard from '@/components/cards/MatchCard.vue'
import TeamCard from '@/components/cards/TeamCard.vue'
import RankingTable from '@/components/RankingTable.vue'

import { shareSocial } from 'ionicons/icons'

</script>
