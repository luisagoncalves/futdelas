<template>
  <ion-card>
    <ion-card-header>
      <ion-card-title>Classificação</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <!-- Estado de carregamento -->
      <div v-if="loading" class="loading-container">
        <ion-spinner name="crescent"></ion-spinner>
        <p>Carregando classificação...</p>
      </div>

      <!-- Estado de erro -->
      <div v-else-if="error" class="error-container">
        <ion-icon :icon="warning" color="danger"></ion-icon>
        <p>Erro ao carregar a classificação</p>
        <ion-button @click="buscarListaClassificacao">Tentar novamente</ion-button>
      </div>

      <!-- Conteúdo principal -->
      <ion-grid v-else>
        <!-- Cabeçalho da tabela -->
        <ion-row class="header-row">
          <ion-col size="5">Time</ion-col>
          <ion-col size="1" class="ion-text-center">J</ion-col>
          <ion-col size="1" class="ion-text-center">V</ion-col>
          <ion-col size="1" class="ion-text-center">E</ion-col>
          <ion-col size="1" class="ion-text-center">D</ion-col>
          <ion-col size="2" class="ion-text-center">PTS</ion-col>
        </ion-row>

        <!-- Times da tabela -->
        <ion-row
          v-for="(classificacao, index) in listaClassificacao"
          :key="index"
        >
          <ion-col size="5" class="ion-align-items-center">
            <div class="team-name">
              <ion-thumbnail class="team-logo">
                <img
                  :src="classificacao.time?.escudo ?? 'src/assets/images/placeholder.png'"
                  :alt="`Escudo de ${classificacao.time?.nome_popular ?? 'Time'}`"
                />
              </ion-thumbnail>
              {{ classificacao.time?.nome_popular ?? 'Time desconhecido' }}
            </div>
          </ion-col>
          <ion-col size="1" class="ion-text-center">{{ classificacao.jogos }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ classificacao.vitorias }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ classificacao.empates }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ classificacao.derrotas }}</ion-col>
          <ion-col size="2" class="ion-text-center">{{ classificacao.pontos }}</ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
  IonGrid,
  IonRow,
  IonCol,
  IonSpinner,
  IonIcon,
  IonButton,
  IonThumbnail
} from '@ionic/vue';
import { warning } from 'ionicons/icons';
import { getClassificacao } from '@/api/services/classificacaoService';

type Time = {
  escudo: string;
  nome_popular: string;
};

type ClassificacaoItem = {
  time?: Time;
  jogos: number;
  vitorias: number;
  empates: number;
  derrotas: number;
  pontos: number;
};

const listaClassificacao = ref<ClassificacaoItem[]>([]);
const loading = ref(false);
const error = ref<string | null>(null);

onMounted(async () => {
  await buscarListaClassificacao();
});

const buscarListaClassificacao = async () => {
  try {
    loading.value = true;
    error.value = null;

    const response = await getClassificacao();

    if (!response) {
      throw new Error('Falha ao carregar dados');
    }

    listaClassificacao.value = response;
  } catch (err: any) {
    console.error('Erro ao buscar classificação:', err);
    error.value = err.message || 'Erro desconhecido';
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.header-row {
  font-weight: bold;
  color: white;
  padding: 12px 0;
}

.team-name {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.team-logo {
  width: 24px;
  height: 24px;
}

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
