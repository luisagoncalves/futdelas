<template>
  <ion-toolbar>
    <ion-buttons slot="secondary">
      <!-- Botão de Configurações com Popover -->
      <ion-button id="abrir-popover-config" aria-label="Configurações">
        <ion-icon :icon="settingsSharp" />
      </ion-button>

      <ion-popover 
        trigger="abrir-popover-config" 
        trigger-action="click" 
        :is-open="mostrarPopover"
        @didDismiss="mostrarPopover = false"
        class="popover-config"
      >
        <ion-content class="conteudo-popover">
          <ion-item lines="none">
            <ion-toggle 
              justify="space-between"
              :checked="notificacoesAtivas" 
              @ionChange="alternarNotificacoes"
              helper-text="Receba notificações das próximas partidas"
            >Notificações</ion-toggle>
          </ion-item>
        </ion-content>
      </ion-popover>
      
      <!-- Botão de Notificações -->
      <ion-button aria-label="Notificações">
        <ion-icon :icon="notifications" />
        <ion-badge v-if="contadorNaoLidas > 0" color="danger" class="badge-notificacao">
          {{ contadorNaoLidas }}
        </ion-badge>
      </ion-button>
    </ion-buttons>
    
    <ion-title class="titulo-app">FutDelas</ion-title>
  </ion-toolbar>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import {
  IonToolbar,
  IonTitle,
  IonButtons,
  IonButton,
  IonIcon,
  IonBadge,
  IonPopover,
  IonContent,
  IonItem,
  IonToggle,
  IonText,
  toastController
} from '@ionic/vue';
import { notifications, settingsSharp } from 'ionicons/icons';
import { solicitarPermissaoNotificacoes } from '@/firebase/config';

const mostrarPopover = ref(false);
const notificacoesAtivas = ref(false);
const contadorNaoLidas = ref(0);

const alternarNotificacoes = async (evento: CustomEvent) => {
  const estaAtivo = evento.detail.checked;
  
  if (estaAtivo) {
    await ativarNotificacoes();
  } else {
    await desativarNotificacoes();
  }
};

const ativarNotificacoes = async () => {
  try {
    const permissao = await Notification.requestPermission();
    
    if (permissao === 'granted') {
      await solicitarPermissaoNotificacoes();
      notificacoesAtivas.value = true;
      await mostrarToast('Notificações ativadas com sucesso');
    } else {
      notificacoesAtivas.value = false;
      await mostrarToast('Permissão para notificações negada');
    }
  } catch (erro) {
    notificacoesAtivas.value = false;
    await mostrarToast('Erro ao ativar notificações');
    console.error('Erro ao ativar notificações:', erro);
  }
};

const desativarNotificacoes = async () => {
  notificacoesAtivas.value = false;
  await mostrarToast('Notificações desativadas');
};

const mostrarToast = async (mensagem: string) => {
  const toast = await toastController.create({
    message: mensagem,
    duration: 2000,
    position: 'top',
  });
  await toast.present();
};
</script>

<style scoped>
.titulo-app {
  font-weight: bold;
  font-size: 1.2rem;
  letter-spacing: 1px;
}

.popover-config {
  --backdrop-opacity: 0.4;
}

.conteudo-popover {
  --background: var(--ion-color-light);
  border-radius: 12px;
}

.rotulo-toggle {
  display: flex;
  flex-direction: column;
}

.texto-ajuda {
  font-size: 0.75rem;
  color: var(--ion-color-medium);
  margin-top: 4px;
}

.badge-notificacao {
  position: absolute;
  top: 4px;
  right: 4px;
  font-size: 0.7rem;
}

ion-button {
  --padding-start: 8px;
  --padding-end: 8px;
  --color: white;
}

ion-icon {
  font-size: 1.4rem;
}
</style>

<style>
/* Estilo global para o popover (resolve o problema do fundo escuro) */
ion-popover::part(backdrop) {
  opacity: 0.4 !important;
  background: var(--ion-color-dark);
}
</style>