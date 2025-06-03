<template>
  <ion-card>
    <ion-card-header>
      <ion-row class="ion-justify-content-between ion-align-items-center">
        <ion-card-title>{{ title }}</ion-card-title>

        <ion-buttons>
          <ion-button @click="shareMatch" :disabled="isSharing">
            <ion-icon slot="icon-only" :icon="icon" />
          </ion-button>
        </ion-buttons>
      </ion-row>
    </ion-card-header>

    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-text>{{ homeTeamName }}</ion-text>
              <ion-img
                :src="homeTeamLogo"
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
                :src="awayTeamLogo"
                style="width: 30px; height: 30px; margin-right: 8px;"
              />
              <ion-text>{{ awayTeamName }}</ion-text>
            </ion-row>
          </ion-col>
        </ion-row>
      </ion-grid>

      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-text>{{ matchDateTime }}</ion-text>
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
  title: String,
  homeTeamName: String,
  homeTeamLogo: String,
  awayTeamName: String,
  awayTeamLogo: String,
  matchDateTime: String,
  placar: String,
  icon: Object,
  matchId: { // Adicione esta prop para identificar a partida
    type: Number,
    required: true
  }
});

const isSharing = ref(false);

const shareMatch = async () => {
  isSharing.value = true;
  
  try {
    // 1. Obter a imagem da API
    const response = await buscarImagemPartida(props.matchId);
    
    // 2. Criar URL temporária para a imagem
    const imageUrl = URL.createObjectURL(response);
    
    // 3. Compartilhar
    if (Capacitor.isNativePlatform()) {
      // Compartilhamento nativo
      const base64Data = await blobToBase64(response);
      await Share.share({
        title: `${props.homeTeamName} vs ${props.awayTeamName}`,
        text: `Confira esta partida: ${props.placar || 'Próximo jogo'} - ${props.matchDateTime}`,
        url: `data:image/png;base64,${base64Data}`,
        dialogTitle: 'Compartilhar partida'
      });
    } else {
      // Compartilhamento web
      if (navigator.share) {
        const file = new File([response], 'partida.png', { type: 'image/png' });
        await navigator.share({
          title: `${props.homeTeamName} vs ${props.awayTeamName}`,
          text: `Confira esta partida: ${props.placar || 'Próximo jogo'} - ${props.matchDateTime}`,
          files: [file]
        });
      } else {
        // Fallback para download
        const link = document.createElement('a');
        link.href = imageUrl;
        link.download = `${props.homeTeamName}-vs-${props.awayTeamName}.png`;
        link.click();
        showToast('Imagem baixada com sucesso!');
      }
    }
  } catch (error) {
    console.error('Erro ao compartilhar:', error);
    showToast('Erro ao compartilhar a partida');
  } finally {
    isSharing.value = false;
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

const showToast = async (message) => {
  const toast = await toastController.create({
    message,
    duration: 3000,
    position: 'top'
  });
  await toast.present();
};
</script>
