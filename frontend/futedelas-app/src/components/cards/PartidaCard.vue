<template>
  <ion-card>
    <ion-card-header>
      <ion-row class="ion-justify-content-between ion-align-items-center">
        <ion-card-title>{{ data }}</ion-card-title>

        <ion-buttons>
          <ion-button @click="shareMatch" :disabled="ativarCompartilhamentoPartida">
            <ion-icon slot="icon-only" :icon="icone" />
          </ion-button>
        </ion-buttons>
      </ion-row>
    </ion-card-header>

    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-text>{{ timeMandante }}</ion-text>
              <ion-img
                :src="timeMandanteEscudo"
                style="width: 30px; height: 30px; margin-left: 8px;"
              />
            </ion-row>
          </ion-col>

          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-text>{{placar}}</ion-text>
            </ion-row>
          </ion-col>

          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-img
                :src="timeVisitanteEscudo"
                style="width: 30px; height: 30px; margin-right: 8px;"
              />
              <ion-text>{{ timeVisitante }}</ion-text>
            </ion-row>
          </ion-col>
        </ion-row>
      </ion-grid>

      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-text>{{ horario }}</ion-text>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
import { ref } from 'vue';
import { 
  IonCard, IonCardHeader, IonCardTitle, IonCardContent,
  IonRow, IonCol, IonGrid, IonText, IonImg,
  IonButton, IonIcon, IonButtons, toastController
} from '@ionic/vue';
import { Share } from '@capacitor/share';
import { buscarImagemPartida } from '@/api/services/partidaService';

const props = defineProps({
  data: String,
  timeMandante: String,
  timeMandanteEscudo: String,
  timeVisitante: String,
  timeVisitanteEscudo: String,
  horario: String,
  placar: String,
  icone: Object,
  partidaId: {
    type: Number,
    required: true
  }
});

const ativarCompartilhamentoPartida = ref(false);

const shareMatch = async () => {
  ativarCompartilhamentoPartida.value = true;
  
  try {
    const response = await buscarImagemPartida(props.partidaId);
    
    const imageUrl = URL.createObjectURL(response);
    
    if (Capacitor.isNativePlatform()) {
      const base64Data = await blobToBase64(response);
      await Share.share({
        title: `${props.timeMandante} vs ${props.timeVisitante}`,
        text: `Confira esta partida: ${props.placar || 'Próximo jogo'} - ${props.horario}`,
        url: `data:image/png;base64,${base64Data}`,
        dialogTitle: 'Compartilhar partida'
      });
    }
  } catch (error) {
    console.error('Erro ao compartilhar:', error);
    mostrarToast('Erro ao compartilhar a partida');
  }
};

const blobToBase64 = (blob) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onloadend = () => {
      const base64data = reader.result.split(',')[1];
      resolve(base64data);
    };
    reader.onerror = reject;
    reader.readAsDataURL(blob);
  });
};

const mostrarToast = async (message) => {
  const toast = await toastController.create({
    message,
    duration: 3000,
    position: 'top'
  });
  await toast.present();
};
</script>
