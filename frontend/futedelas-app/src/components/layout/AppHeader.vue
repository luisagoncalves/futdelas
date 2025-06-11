<template>
    <ion-toolbar>
      <ion-buttons slot="secondary">
        <ion-button id="open-notification-popover">
          <ion-icon :icon="settingsSharp" />
        </ion-button>

        <ion-popover trigger="open-notification-popover" trigger-action="click" :is-open="showPopover"
          @didDismiss="showPopover = false">
          <ion-content class="ion-padding">
            <ion-item lines="none">
              <ion-toggle 
                helper-text="Receba notificações das próximas partidas"
                justify="space-between"
                :checked="notificationsEnabled" 
                @ionChange="handleNotificationToggle"
              >Notificações</ion-toggle>
            </ion-item>
          </ion-content>
        </ion-popover>
        
        <ion-button>
          <ion-icon :icon="notifications"></ion-icon>
          <ion-badge v-if="unreadCount > 0" color="danger">{{ unreadCount }}</ion-badge>
        </ion-button>
      </ion-buttons>
      <ion-title>FutDelas</ion-title>
    </ion-toolbar>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonButton,
  IonIcon,
  IonBadge,
  IonPopover,
  IonContent,
  IonItem,
  IonLabel,
  IonToggle,
  toastController
} from '@ionic/vue';
import { notifications, settingsSharp } from 'ionicons/icons';
import { UsuarioNotificacao } from '@/api/interfaces/UsuarioNotificacao';
import { registrarNotificacao, atualizarNotificacao } from '@/api/services/notificacaoService';
import { solicitarPermissaoNotificacoes } from '@/firebase/config';

const showPopover = ref(false);
const notificationsEnabled = ref(false);
const unreadCount = ref(0);

const handleNotificationToggle = async (event: CustomEvent) => {
  const isEnabled = event.detail.checked;
  
  if (isEnabled) {
    await ativarNotificacao();
  } else {
    await desativarNotificacao();
  }
};

const ativarNotificacao = async () => {
  try {
    const permission = await Notification.requestPermission();
    
    if (permission === 'granted') {
      const dispositivoToken = await solicitarPermissaoNotificacoes();
      notificationsEnabled.value = true;
      await showToast('Notificações ativadas com sucesso');
    } else {
      notificationsEnabled.value = false;
      await showToast('Permissão para notificações negada');
    }
  } catch (error) {
    notificationsEnabled.value = false;
    await showToast('Erro ao ativar notificações');
  }
};

const desativarNotificacao = async () => {
  notificationsEnabled.value = false;
  await showToast('Notificações desativadas');
};

const showToast = async (message: string) => {
  const toast = await toastController.create({
    message,
    duration: 2000,
    position: 'top'
  });
  await toast.present();
};
</script>

<style scoped>
.timestamp {
  font-size: 0.8rem;
  color: var(--ion-color-medium);
}
</style>