<template>
  <ion-card>
    <ion-card-header>
      <ion-card-title>Campeonato</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <div v-if="loading" class="loading-container">
        <ion-spinner name="crescent"></ion-spinner>
        <p>Carregando informações...</p>
      </div>

      <div v-else-if="error" class="error-container">
        <ion-icon :icon="warning" color="danger"></ion-icon>
        <p>Erro ao carregar informações</p>
        <ion-button @click="buscarListaClassificacao">Tentar novamente</ion-button>
      </div>

      <ion-grid v-else>
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
import { warning } from 'ionicons/icons';

const campeonato = ref(new Campeonato());
const loading = ref(false);
const error = ref(false);

const buscarCampeonato = async () => {
  loading.value = true;
  error.value = false;
  try {
    campeonato.value = await getCampeonato();
  } catch (e) {
    console.error('Erro ao buscar campeonato:', e);
    error.value = true;
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  buscarCampeonato();
});
</script>

<style scoped>
.loading-container,
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  gap: 12px;
}

.error-container ion-icon {
  font-size: 48px;
}
</style>
 