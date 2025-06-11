<template>
  <ion-content>
    <ion-segment value="detalhes" @ionChange="mudarTela($event)">
      <ion-segment-button value="detalhes">
        <ion-label>Detalhes</ion-label>
      </ion-segment-button>
      <ion-segment-button value="partidas">
        <ion-label>Partidas</ion-label>
      </ion-segment-button>
      <ion-segment-button value="times">
        <ion-label>Times</ion-label>
      </ion-segment-button>
    </ion-segment>

    <div v-if="telaExibida === 'detalhes'">
      <DetalhesView />
    </div>
    <div v-if="telaExibida === 'partidas'">
      <PartidasView />
    </div>
    <div v-if="telaExibida === 'times'">
      <TimesView />
    </div>
  </ion-content>
</template>
<script setup lang="ts">
import { ref } from 'vue';
import {
  IonContent,
  IonSegment,
  IonSegmentButton,
  IonLabel
} from '@ionic/vue';
import DetalhesView from '@/views/DetalhesView.vue';
import PartidasView from '@/views/PartidasView.vue';
import TimesView from '@/views/TimesView.vue';

const telaExibida = ref('detalhes');

const mudarTela = (ev: CustomEvent) => {
  telaExibida.value = ev.detail.value;
};
</script>
