<template>
  <ion-card class="card-partida">
    <ion-card-header class="cabecalho-card">
      <ion-row class="linha-cabecalho">
        <ion-card-title class="data-partida">{{ dataFormatada }}</ion-card-title>

        <ion-buttons>
          <ion-button 
            @click="compartilharPartida" 
            :disabled="compartilhamentoAtivo"
            class="botao-compartilhar"
          >
            <ion-icon slot="icon-only" :icon="iconeCompartilhar" />
          </ion-button>
        </ion-buttons>
      </ion-row>
    </ion-card-header>

    <ion-card-content class="conteudo-card">
      <!-- Linha dos times e placar -->
      <div class="linha-principal">
        <div class="time-container time-mandante">
          <ion-img
            :src="escudoTimeMandante"
            class="escudo-time"
            :alt="`Escudo do ${nomeTimeMandante}`"
          />
          <ion-text class="nome-time">{{ nomeTimeMandante }}</ion-text>
        </div>

        <div class="placar-container">
          <ion-text class="placar">{{ placar || 'x' }}</ion-text>
        </div>

        <div class="time-container time-visitante">
          <ion-text class="nome-time">{{ nomeTimeVisitante }}</ion-text>
          <ion-img
            :src="escudoTimeVisitante"
            class="escudo-time"
            :alt="`Escudo do ${nomeTimeVisitante}`"
          />
        </div>
      </div>

      <!-- Informações adicionais -->
      <div class="informacoes-adicionais">
        <ion-text class="horario-partida">
          <ion-icon :icon="iconeRelogio" slot="start" />
          {{ horario }}
        </ion-text>
      </div>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
import { ref } from 'vue';
import { 
  IonCard, IonCardHeader, IonCardTitle, IonCardContent,
  IonRow, IonCol, IonText, IonImg,
  IonButton, IonIcon, IonButtons, toastController
} from '@ionic/vue';
import { Share } from '@capacitor/share';
import { timeOutline as iconeRelogio } from 'ionicons/icons';
import { buscarImagemPartida } from '@/api/services/partidaService';

const props = defineProps({
  dataFormatada: {
    type: String,
    required: true
  },
  nomeTimeMandante: {
    type: String,
    required: true
  },
  escudoTimeMandante: {
    type: String,
    required: true
  },
  nomeTimeVisitante: {
    type: String,
    required: true
  },
  escudoTimeVisitante: {
    type: String,
    required: true
  },
  horario: {
    type: String,
    required: true
  },
  placar: {
    type: String,
    default: ''
  },
  iconeCompartilhar: {
    type: Object,
    required: true
  },
  idPartida: {
    type: Number,
    required: true
  }
});

const compartilhamentoAtivo = ref(false);

const compartilharPartida = async () => {
  compartilhamentoAtivo.value = true;
  
  try {
    const resposta = await buscarImagemPartida(props.idPartida);
    const urlImagem = URL.createObjectURL(resposta);
    
    if (Capacitor.isNativePlatform()) {
      const imagemBase64 = await converterBlobParaBase64(resposta);
      await Share.share({
        title: `${props.nomeTimeMandante} vs ${props.nomeTimeVisitante}`,
        text: `Confira esta partida: ${props.placar || 'Próximo jogo'} - ${props.horario}`,
        url: `data:image/png;base64,${imagemBase64}`,
        dialogTitle: 'Compartilhar partida'
      });
    } else {
      await mostrarMensagem('Compartilhamento disponível apenas no app');
    }
  } catch (erro) {
    console.error('Erro ao compartilhar:', erro);
    await mostrarMensagem('Falha ao compartilhar a partida');
  } finally {
    compartilhamentoAtivo.value = false;
  }
};

const converterBlobParaBase64 = (blob) => {
  return new Promise((resolve, reject) => {
    const leitor = new FileReader();
    leitor.onloadend = () => {
      const dadosBase64 = leitor.result.split(',')[1];
      resolve(dadosBase64);
    };
    leitor.onerror = reject;
    leitor.readAsDataURL(blob);
  });
};

const mostrarMensagem = async (mensagem) => {
  const toast = await toastController.create({
    message: mensagem,
    duration: 3000,
    position: 'top',
    color: 'medium'
  });
  await toast.present();
};
</script>

<style scoped>
.card-partida {
  border-radius: 12px;
  margin: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease;
}

.card-partida:hover {
  transform: translateY(-2px);
}

.cabecalho-card {
  padding: 12px 16px;
  background-color: var(--ion-color-light);
}

.linha-cabecalho {
  align-items: center;
}

.data-partida {
  font-size: 1rem;
  font-weight: 500;
  color: var(--ion-color-medium);
}

.botao-compartilhar {
  --padding-start: 4px;
  --padding-end: 4px;
}

.conteudo-card {
  padding: 16px;
}

.linha-principal {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}

.time-container {
  display: flex;
  align-items: center;
  flex: 1;
}

.time-mandante {
  justify-content: flex-end;
}

.time-visitante {
  justify-content: flex-start;
}

.nome-time {
  font-weight: 500;
  font-size: 1rem;
  margin: 0 8px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 120px;
}

.escudo-time {
  width: 32px;
  height: 32px;
  object-fit: contain;
}

.placar-container {
  min-width: 60px;
  text-align: center;
}

.placar {
  font-size: 1.4rem;
  font-weight: 600;
}

.informacoes-adicionais {
  text-align: center;
  margin-top: 8px;
}

.horario-partida {
  font-size: 0.9rem;
  color: var(--ion-color-medium);
  display: inline-flex;
  align-items: center;
}

.horario-partida ion-icon {
  margin-right: 4px;
  font-size: 1rem;
}

@media (max-width: 400px) {
  .nome-time {
    max-width: 80px;
    font-size: 0.9rem;
  }
  
  .placar {
    font-size: 1.2rem;
  }
}
</style>