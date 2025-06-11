<template>
  <!-- Estado de Carregamento -->
  <div v-if="carregando" class="container-carregando">
    <ion-spinner name="crescent"></ion-spinner>
    <ion-text>Carregando informações do campeonato...</ion-text>
  </div>

  <div v-else-if="erro" class="container-erro">
    <ion-icon :icon="alerta" color="danger"></ion-icon>
    <ion-text color="danger">Erro ao carregar o campeonato</ion-text>
    <ion-button @click="carregarCampeonato" fill="clear">
      Tentar novamente
    </ion-button>
  </div>

  <ion-card v-else>
    <ion-card-header>
      <ion-card-title>Campeonato</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-row class="ion-justify-content-center ion-align-items-center">
              <ion-img 
                :src="campeonato.logo" 
                alt="Logo do campeonato" 
                class="logo-campeonato"
                @ionError="imagemPadrao"
              ></ion-img>
            </ion-row>
            <ion-row class="ion-justify-content-center ion-align-items-center" style="flex-direction: column;">
              <ion-text>{{ campeonato.nome || 'Nome não disponível' }}</ion-text>
            </ion-row>
          </ion-col>
        </ion-row>

        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-text>{{ campeonato.rodada_atual?.nome || 'Rodada não informada' }}</ion-text>
          </ion-col>
          <ion-col class="ion-text-center">
            <ion-text>{{ campeonato.fase_atual?.nome || 'Fase não informada' }}</ion-text>
          </ion-col>
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
  IonText,
  IonImg,
  IonSpinner,
  IonIcon,
  IonButton
} from '@ionic/vue';
import { alert } from 'ionicons/icons';
import { buscarCampeonato } from '@/api/services/campeonatoService';
import { Campeonato } from '@/api/interfaces/Campeonato';

// Dados reativos
const campeonato = ref<Campeonato>({
  logo: '',
  nome: '',
  rodada_atual: {
    nome: '',
    slug: '',
    rodada: 0,
    status: ''
  },
  fase_atual: {
    nome: '',
    fase_id: 0,
    slug: '',
    tipo: ''
  },
  slug: '',
  nome_popular: '',
  edicao_atual: {
    id_edicao: 0,
    temporada: '',
    nome: '',
    nome_popular: '',
    slug: ''
  },
  status: '',
  tipo: '',
  regiao: ''
});
const carregando = ref(false);
const erro = ref(false);
const alerta = alert;

const carregarCampeonato = async () => {
  carregando.value = true;
  erro.value = false;
  
  try {
    const dados = await buscarCampeonato();
    campeonato.value = {
      ...campeonato.value,
      ...dados
    };
  } catch (e) {
    console.error('Erro ao buscar campeonato:', e);
    erro.value = true;
  } finally {
    carregando.value = false;
  }
};

const imagemPadrao = (event: Event) => {
  const imgElement = event.target as HTMLImageElement;
  imgElement.src = 'https://via.placeholder.com/70?text=Sem+Logo';
};

onMounted(() => {
  carregarCampeonato();
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

.logo-campeonato {
  width: 70px; 
  height: 70px;
  object-fit: contain;
}

ion-spinner {
  width: 40px;
  height: 40px;
}
</style>