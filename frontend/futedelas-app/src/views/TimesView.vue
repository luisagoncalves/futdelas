<template>
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

  <div v-else>
    <TimeCard 
      v-for="time in times"
      :key="time.time_id"
      :nome-time="time.nome_popular" 
      :escudo-time="time.escudo"
      :time-id="time.time_id"
      :is-favorito="timeFavorito?.timeId === time.time_id"
      :tem-outro-favorito="!!timeFavorito && timeFavorito.timeId !== time.time_id"
      @toggle-favorito="handleToggleFavorito"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { buscarTimes } from '@/api/services/timeService';
import TimeCard from '@/components/cards/TimeCard.vue';
import { Time } from '@/api/interfaces/Time';
import { alert } from 'ionicons/icons';
import { IonSpinner, IonButton, IonIcon, IonText, toastController } from '@ionic/vue';
import { getCurrentUserId } from '@/api/services/auth';
import { buscarTimeFavorito, registrarTimeFavorito, removerTimeFavorito } from '@/api/services/timeFavoritoService';
import { TimeFavorito } from '@/api/interfaces/TimeFavorito';

const times = ref<Time[]>([]);
const carregando = ref(true);
const erroCarregamento = ref(false);
const timeFavorito = ref<TimeFavorito | null>(null);
const userId = getCurrentUserId();

const carregarTimes = async () => {
  try {
    carregando.value = true;
    erroCarregamento.value = false;
    times.value = await buscarTimes();
  } catch (error) {
    erroCarregamento.value = true;
    console.error('Erro ao carregar times:', error);
  } finally {
    carregando.value = false;
  }
};

const carregarTimeFavorito = async () => {
  try {
    timeFavorito.value = await buscarTimeFavorito(userId);
  } catch (error) {
    console.error('Erro ao carregar time favorito:', error);
    timeFavorito.value = null;
  }
};

const handleToggleFavorito = async (timeId: number) => {
  try {
    carregando.value = true;

    if (timeFavorito.value?.timeId === timeId) {
      await removerTimeFavorito(timeFavorito.value.id);
      timeFavorito.value = null;
      await mostrarToast("Time removido dos favoritos");
      return;
    }
    
    if (!timeFavorito.value) {
      const novoFavorito = await registrarTimeFavorito(timeId, userId);
      timeFavorito.value = novoFavorito;
      await mostrarToast("Time favoritado com sucesso");
    } else {
      await mostrarToast("Remova o time favorito atual antes de selecionar outro");
    }
  } catch (error) {
    console.error('Erro ao alterar favorito:', error);
    await mostrarToast("Erro ao alterar favorito");
  } finally {
    carregando.value = false;
    await carregarTimeFavorito();
  }
};

const mostrarToast = async (mensagem: string) => {
  const toast = await toastController.create({
    message: mensagem,
    duration: 2000,
    position: 'top',
  });
  await toast.present();
};

onMounted(async () => {
  await Promise.all([carregarTimes(), carregarTimeFavorito()]);
});
</script>

<style scoped>
.estado-carregamento,
.estado-erro {
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
</style>