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

</template>

<script setup>
import { ref } from 'vue';
import { notifications } from 'ionicons/icons';

const showAlert = ref(false);

</script>
