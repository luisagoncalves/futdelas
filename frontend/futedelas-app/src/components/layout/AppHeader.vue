<template>
  <ion-header>
    <ion-toolbar>
      <ion-buttons slot="secondary">
        <ion-button id="open-notification-popover">
          <ion-icon :icon="settingsSharp"></ion-icon>
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
  </ion-header>
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
import { getMessaging, getToken, onMessage } from 'firebase/messaging';
import { app } from '@/firebase/config';

const messaging = getMessaging(app);
const showPopover = ref(false);
const notificationsEnabled = ref(false);
const unreadCount = ref(0);

const handleNotificationToggle = async (event: CustomEvent) => {
  const isEnabled = event.detail.checked;
  
  if (isEnabled) {
    await enableNotifications();
  } else {
    await disableNotifications();
  }
};

const enableNotifications = async () => {
  try {
    const permission = await Notification.requestPermission();
    
    if (permission === 'granted') {
      await getFCMToken();
      setupForegroundMessageHandler();
      notificationsEnabled.value = true;
      await showToast('Notificações ativadas com sucesso');
    } else {
      notificationsEnabled.value = false;
      await showToast('Permissão para notificações negada');
    }
  } catch (error) {
    console.error('Erro ao ativar notificações:', error);
    notificationsEnabled.value = false;
    await showToast('Erro ao ativar notificações');
  }
};

const disableNotifications = async () => {
  notificationsEnabled.value = false;
  await showToast('Notificações desativadas');
};

const getFCMToken = async () => {
  try {
    const currentToken = await getToken(messaging, {
      vapidKey: import.meta.env.VITE_FIREBASE_VAPID_KEY
    });
    
    if (currentToken) {
      console.log('Token FCM:', currentToken);
      return currentToken;
    }
    throw new Error('Não foi possível obter o token FCM');
  } catch (error) {
    throw error;
  }
};

const setupForegroundMessageHandler = () => {
  onMessage(messaging, (payload) => {
    if (!notificationsEnabled.value) return;
    
    unreadCount.value += 1;
    showNotification(payload.notification);
  });
};

const showNotification = (notification: { title?: string; body?: string } = {}) => {
  if (Notification.permission === 'granted' && notification.title) {
    new Notification(notification.title, {
      body: notification.body,
    });
  }
};

const showToast = async (message: string) => {
  const toast = await toastController.create({
    message,
    duration: 2000,
    position: 'top'
  });
  await toast.present();
};

onMounted(async () => {
  if (Notification.permission === 'granted') {
    notificationsEnabled.value = true;
    await getFCMToken();
    setupForegroundMessageHandler();
  }
});
</script>

<style scoped>
.timestamp {
  font-size: 0.8rem;
  color: var(--ion-color-medium);
}
</style>