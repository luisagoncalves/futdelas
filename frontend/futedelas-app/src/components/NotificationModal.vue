<template>
  <ion-alert 
    :is-open="isOpen" 
    header="Deseja receber notificações sobre as próximas partidas do campeonato?"
    :buttons="alertButtons" 
    @didDismiss="handleDismiss" 
  />
</template>

<script setup lang="ts">
import { IonAlert } from '@ionic/vue';
import { ref, defineProps, defineEmits, onMounted } from 'vue';
import { useNotificationService } from '@/services/notificationService';

// Tipos TypeScript
interface Props {
  isOpen: boolean;
}

interface Emits {
  (e: 'update:isOpen', value: boolean): void;
  (e: 'notification-status', status: NotificationStatus): void;
}

type NotificationStatus = {
  granted: boolean;
  type?: 'push' | 'local' | 'browser';
  token?: string;
  error?: Error;
};

const props = defineProps<Props>();
const emits = defineEmits<Emits>();

// Serviço de notificação centralizado
const notificationService = useNotificationService();

const alertButtons = [
  {
    text: 'Cancelar',
    role: 'cancel'
  },
  {
    text: 'Confirmar',
    role: 'confirm',
    handler: async () => {
      const result = await notificationService.requestPermission();
      emits('notification-status', result);
      
      if (result.granted) {
        await registerDeviceIfNeeded(result);
      }
    }
  }
];

const handleDismiss = () => {
  emits('update:isOpen', false);
};

/**
 * Registra o dispositivo no backend se for push notification
 */
const registerDeviceIfNeeded = async (status: NotificationStatus) => {
  if (status.type === 'push' && status.token) {
    try {
      await notificationService.registerDevice({
        token: status.token,
        platform: notificationService.getPlatform(),
        userId: 'current-user-id' // Substituir pelo ID real
      });
    } catch (error) {
      emits('notification-status', {
        granted: false,
        error: error as Error
      });
    }
  }
};

// Expõe métodos do serviço para o componente pai
defineExpose({
  scheduleNotification: notificationService.schedule,
  cancelNotification: notificationService.cancel,
  requestPermission: notificationService.requestPermission
});
</script>