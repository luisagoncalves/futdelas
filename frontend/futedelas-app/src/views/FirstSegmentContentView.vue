<template>
    <ion-segment-content id="first">
      <ion-grid>
        <CompeticaoCard />

        <TimeFavorito />

        <ProximaPartidaCard />

        <TabelaClassificacao />
      </ion-grid>
    </ion-segment-content>
</template>

<script setup lang="ts">
import CompeticaoCard from '@/components/cards/CompeticaoCard.vue'
import ProximaPartidaCard from '@/components/cards/ProximaPartidaCard.vue'
import TabelaClassificacao from '@/components/TabelaClassificacao.vue'
import TimeFavorito from '@/components/cards/TimeFavorito.vue'
</script>