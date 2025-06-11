<template>
  <div v-if="carregando" class="container-carregando">
    <ion-spinner name="crescent"></ion-spinner>
    <ion-text>Carregando informações do time...</ion-text>
  </div>

  <div v-else-if="erro" class="container-erro">
    <ion-icon :icon="alerta" color="danger"></ion-icon>
    <ion-text color="danger">Erro ao carregar time favorito</ion-text>
    <ion-button @click="carregarTimeFavorito" fill="clear">
      Tentar novamente
    </ion-button>
  </div>

  <ion-card v-else class="time-card">
    <ion-card-header>
      <ion-card-title>Time favorito</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <ion-row class="ion-align-items-center ion-justify-content-between">
        <ion-row class="ion-align-items-center">
          <ion-img :src="time.escudo" :alt="time.nome_popular" class="time-logo"></ion-img>
          <ion-text class="time-nome">{{ time.nome_popular || 'Nome não disponível' }}</ion-text>
        </ion-row>

        <ion-icon :icon="estrela" color="warning"></ion-icon>
      </ion-row>
    </ion-card-content>
  </ion-card>
</template>

<script setup lang="ts">
import { Time } from '@/api/interfaces/Time';
import { buscarTimeFavorito } from '@/api/services/timeService';
import { 
  IonCard, 
  IonCardHeader, 
  IonCardTitle, 
  IonCardContent,
  IonRow,
  IonImg,
  IonText,
  IonIcon,
  IonSpinner,
  IonButton
} from '@ionic/vue';
import { star, alert } from 'ionicons/icons';
import { onMounted, ref } from 'vue';

const carregando = ref(false);
const erro = ref(false);
const alerta = alert;

const time = ref<Time>({
  time_id: 1,
  nome_popular: 'Meu Time',
  escudo: 'https://via.placeholder.com/50?text=Logo',
  sigla: ''
});

const carregarTimeFavorito = async () => {
  carregando.value = true;
  erro.value = false;
  
  try {
    const dados = await buscarTimeFavorito(1);
    time.value = {
      ...time.value,
      ...dados
    };
  } catch (e) {
    console.error('Erro ao buscar time:', e);
    erro.value = true;
  } finally {
    carregando.value = false;
  }
};

const estrela = star;

onMounted(() => {
  carregarTimeFavorito();
});
</script>

<style scoped>
.container-carregando, 
.container-erro {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
  gap: 16px;
}
ion-spinner {
  width: 40px;
  height: 40px;
}
.time-card {
  margin: 16px;
  border-radius: 12px;
}

.time-logo {
  width: 50px;
  height: 50px;
  margin-right: 12px;
}

.time-nome {
  font-size: 1.1rem;
  font-weight: 500;
}
</style>