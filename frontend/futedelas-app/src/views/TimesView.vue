<template>
  <div class="lista-times-container">
    <div v-if="carregando" class="estado-carregamento">
      <ion-spinner name="crescent" color="primary"></ion-spinner>
      <p>Carregando times...</p>
    </div>

    <div v-else-if="erroCarregamento" class="estado-erro">
      <ion-icon :icon="alert" color="danger" size="large"></ion-icon>
      <ion-text>Erro ao carregar times</ion-text>
      <ion-button @click="carregarTimes" fill="clear">
        Tentar novamente
      </ion-button>
    </div>

    <template v-else>
      <template v-if="times.length > 0">
        <TimeCard 
          v-for="time in times"
          :key="time.time_id"
          :nome-time="time.nome_popular" 
          :escudo-time="time.escudo"
          :isInitiallyFavorite="time.timeFavorito || false"
        />
      </template>
      
      <!-- Estado Vazio -->
      <div v-else class="estado-vazio">
        <ion-icon :icon="sadOutline" size="large"></ion-icon>
        <p>Nenhum time disponível no momento</p>
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getTimes } from '@/api/services/timeService';
import TimeCard from '@/components/cards/TimeCard.vue';
import { Time } from '@/api/interfaces/Time';
import { alert, sadOutline } from 'ionicons/icons';
import { IonSpinner, IonButton, IonIcon } from '@ionic/vue';

const times = ref<Time[]>([]);
const carregando = ref(true);
const erroCarregamento = ref(false);
const mensagemErro = ref('');

const carregarTimes = async () => {
  try {
    carregando.value = true;
    erroCarregamento.value = false;
    mensagemErro.value = '';
    
    const response = await getTimes();
    times.value = response.map(time => ({
      ...time,
      timeFavorito: false
    }));
  } catch (error) {
    erroCarregamento.value = true;
    mensagemErro.value = error instanceof Error ? error.message : 'Ocorreu um erro desconhecido';
  } finally {
    carregando.value = false;
  }
};

onMounted(carregarTimes);
</script>

<style scoped>
.lista-times-container {
  padding: 12px;
}

.estado-carregamento,
.estado-erro,
.estado-vazio {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 32px;
  gap: 16px;
  color: var(--ion-color-medium);
}

.estado-carregamento ion-spinner {
  width: 48px;
  height: 48px;
}

.estado-erro {
  color: var(--ion-color-danger);
}

.estado-erro h3 {
  margin: 0;
  font-weight: bold;
}

.estado-vazio ion-icon {
  color: var(--ion-color-medium-shade);
  font-size: 48px;
}
</style>