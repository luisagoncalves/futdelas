<template>
  <ion-card>
    <ion-card-header>
      <ion-card-title>Classificação</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <!-- Estado de carregamento -->
      <div v-if="loading" class="loading-container">
        <ion-spinner name="crescent"></ion-spinner>
        <p>Carregando dados...</p>
      </div>

      <!-- Estado de erro -->
      <div v-else-if="error" class="error-container">
        <ion-icon :icon="warning" color="danger"></ion-icon>
        <p>Erro ao carregar a classificação</p>
        <ion-button @click="fetchStandings">Tentar novamente</ion-button>
      </div>

      <!-- Conteúdo principal -->
      <ion-grid v-else>
        <!-- Cabeçalho da tabela -->
        <ion-row class="header-row">
          <ion-col size="5">Equipe</ion-col>
          <ion-col size="1" class="ion-text-center">J</ion-col>
          <ion-col size="1" class="ion-text-center">V</ion-col>
          <ion-col size="1" class="ion-text-center">E</ion-col>
          <ion-col size="1" class="ion-text-center">D</ion-col>
          <ion-col size="2" class="ion-text-center">PTS</ion-col>
        </ion-row>

        <!-- Times da tabela -->
        <ion-row
          v-for="(team, index) in standings"
          :key="team.id || index"
          :class="index % 2 === 0 ? 'even-row' : 'odd-row'"
        >
          <ion-col size="5" class="ion-align-items-center">
            <div class="position-label" v-if="index < 8">{{ index + 1 }}</div>
            <div class="team-name">
              <ion-thumbnail class="team-logo">
                <img :src="team.logo || defaultLogo" :alt="team.name" />
              </ion-thumbnail>
              {{ team.name }}
            </div>
          </ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.gamesPlayed }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.wins }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.draws }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.losses }}</ion-col>
          <ion-col size="2" class="ion-text-center">{{ team.points }}</ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
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

// Estados reativos
const standings = ref([]);
const loading = ref(true);
const error = ref(null);
const defaultLogo = ref('assets/images/default-team-logo.png');

// Função para buscar dados da API
const fetchStandings = async () => {
  try {
    loading.value = true;
    error.value = null;
    
    // Simulação de chamada API - substitua pela sua implementação real
    const response = await fetch('https://api.example.com/standings');
    
    if (!response.ok) {
      throw new Error('Falha ao carregar dados');
    }
    
    const data = await response.json();
    standings.value = data.map(team => ({
      id: team.id,
      name: team.name,
      logo: team.logoUrl,
      gamesPlayed: team.jogos,
      wins: team.vitorias,
      draws: team.empates,
      losses: team.derrotas,
      points: team.pontos
    }));
    
  } catch (err) {
    console.error('Erro ao buscar classificação:', err);
    error.value = err.message || 'Erro desconhecido';
  } finally {
    loading.value = false;
  }
};

// Busca inicial dos dados
onMounted(fetchStandings);
</script>

<style scoped>
.header-row {
  font-weight: bold;
  background-color: var(--ion-color-primary);
  color: white;
  padding: 12px 0;
}

.even-row {
  background-color: #f5f5f5;
}

.odd-row {
  background-color: #ffffff;
}

.position-label {
  display: inline-block;
  background-color: var(--ion-color-success);
  color: white;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  text-align: center;
  line-height: 24px;
  font-size: 12px;
  margin-right: 8px;
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