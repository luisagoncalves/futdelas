<template>
    <!-- Estado de Carregamento -->
  <div v-if="carregando" class="container-carregando">
    <ion-spinner name="crescent"></ion-spinner>
    <ion-text>Carregando informações da classificação...</ion-text>
  </div>

  <!-- Estado de Erro -->
  <div v-else-if="erro" class="container-erro">
    <ion-icon :icon="alerta" color="danger"></ion-icon>
    <ion-text color="danger">Erro ao carregar a classificação</ion-text>
    <ion-button @click="carregarClassificacao" fill="clear">
      Tentar novamente
    </ion-button>
  </div>

  <ion-card v-else class="card-classificacao">
    <ion-card-header>
      <ion-card-title>Tabela de Classificação</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <div class="tabela-container">
        <!-- Cabeçalho da tabela -->
        <div class="cabecalho-tabela">
          <div class="coluna-time">Time</div>
          <div class="coluna-numero">J</div>
          <div class="coluna-numero">V</div>
          <div class="coluna-numero">E</div>
          <div class="coluna-numero">D</div>
          <div class="coluna-pontos">PTS</div>
        </div>

        <!-- Linhas dos times -->
        <div 
          v-for="(time, index) in classificacao" 
          :key="index"
          class="linha-time"
          :class="{ 'linha-par': index % 2 === 0 }"
        >
          <div class="coluna-time">
            <div class="time-info">
              <ion-thumbnail class="escudo-time">
                <img
                  :src="time.time?.escudo ?? 'src/assets/images/placeholder.png'"
                  :alt="`Escudo de ${time.time?.nome_popular ?? 'Time'}`"
                />
              </ion-thumbnail>
              <span class="nome-time">{{ time.time?.nome_popular ?? 'Time desconhecido' }}</span>
            </div>
          </div>
          <div class="coluna-numero">{{ time.jogos }}</div>
          <div class="coluna-numero">{{ time.vitorias }}</div>
          <div class="coluna-numero">{{ time.empates }}</div>
          <div class="coluna-numero">{{ time.derrotas }}</div>
          <div class="coluna-pontos">{{ time.pontos }}</div>
        </div>
      </div>
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
  IonSpinner,
  IonIcon,
  IonButton,
  IonThumbnail,
  IonText
} from '@ionic/vue';
import { getClassificacao } from '@/api/services/classificacaoService';
import { alert } from 'ionicons/icons';

interface Time {
  escudo: string;
  nome_popular: string;
}

interface ClassificacaoTime {
  time?: Time;
  jogos: number;
  vitorias: number;
  empates: number;
  derrotas: number;
  pontos: number;
}

const classificacao = ref<ClassificacaoTime[]>([]);
const carregando = ref(false);
const erro = ref<string | null>(null);
const alerta = alert;

onMounted(async () => {
  await carregarClassificacao();
});

const carregarClassificacao = async () => {
  try {
    carregando.value = true;
    erro.value = null;

    const resposta = await getClassificacao();

    if (!resposta) {
      throw new Error('Falha ao carregar dados da classificação');
    }

    classificacao.value = resposta;
  } catch (err: any) {
    erro.value = err.message || 'Erro desconhecido ao carregar dados';
  } finally {
    carregando.value = false;
  }
};
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
.card-classificacao {
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin: 16px;
}

.tabela-container {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
}

.cabecalho-tabela {
  display: flex;
  background-color: var(--ion-color-primary);
  color: white;
  padding: 12px 8px;
  font-weight: 600;
  border-radius: 8px 8px 0 0;
}

.linha-time {
  display: flex;
  padding: 10px 8px;
  align-items: center;
  border-bottom: 1px solid #f0f0f0;
}

.linha-par {
  background-color: #f9f9f9;
}

.coluna-time {
  flex: 3;
  min-width: 0;
}

.coluna-numero {
  flex: 1;
  text-align: center;
  font-weight: 500;
}

.coluna-pontos {
  flex: 1.2;
  text-align: center;
  font-weight: 600;
  color: var(--ion-color-primary);
}

.time-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.escudo-time {
  width: 28px;
  height: 28px;
  min-width: 28px;
  min-height: 28px;
}

.escudo-time img {
  object-fit: contain;
}

.nome-time {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.estado-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 32px 16px;
  gap: 16px;
  text-align: center;
}

.estado-erro ion-icon {
  font-size: 48px;
  margin-bottom: 8px;
}

@media (max-width: 480px) {
  .nome-time {
    max-width: 120px;
  }
  
  .cabecalho-tabela,
  .linha-time {
    padding: 10px 4px;
    font-size: 0.9rem;
  }
}
</style>