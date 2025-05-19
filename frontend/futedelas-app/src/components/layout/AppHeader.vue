<template>
  <ion-header>
    <ion-toolbar>
      <ion-buttons slot="secondary">
        <ion-button @click="showAlert = true">
          <ion-icon slot="icon-only" :icon="notifications"></ion-icon>
        </ion-button>
      </ion-buttons>
      <ion-title>FutDelas</ion-title>
    </ion-toolbar>
  </ion-header>

   <NotificationModal
      v-model:isOpen="showAlert" 
  @confirm="onConfirmNotifications"
  @notification-permission="handlePermission"
  @notification-error="handleError"
    />
</template>

<script setup>
import { ref } from 'vue';
import NotificationModal from '@/components/NotificationModal.vue';
import { notifications } from 'ionicons/icons';

const showAlert = ref(false);

// No script do componente pai:
const onConfirmNotifications = async () => {
  // Pode agendar notificações aqui
  await scheduleNextMatchNotification({
    time: new Date('2025-05-18T18:42:00'),
    teamA: 'Time A',
    teamB: 'Time B'
  });
};
</script>
