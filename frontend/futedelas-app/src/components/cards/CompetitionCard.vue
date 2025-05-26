<template>
  <ion-card>
    <ion-card-header>
      <ion-card-title>Campeonato</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-row class="ion-justify-content-center ion-align-items-center">
              <ion-img :src="campeonato.logo" alt="Logo do time" style="width: 70px; height: 70px;"></ion-img>
            </ion-row>
            <ion-row class="ion-justify-content-center ion-align-items-center" style="flex-direction: column;">
              <ion-text>{{ campeonato.nome }}</ion-text>
            </ion-row>
          </ion-col>
        </ion-row>

        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-text>{{campeonato.rodada_atual.nome}}</ion-text>
          </ion-col>
          <ion-col class="ion-text-center">
            <ion-text>{{campeonato.fase_atual.nome}}</ion-text>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup lang="ts">
import { getCampeonato } from '@/api/services/campeonatoService';
import { Campeonato } from '@/api/interfaces/Campeonato';
import { ref, onMounted } from 'vue';

const campeonato = ref(new Campeonato());

onMounted(async () => {
  campeonato.value = await getCampeonato();
});
</script>
 