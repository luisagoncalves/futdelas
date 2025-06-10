<template>
  <div>
    <div v-if="existeCarregamento" class="loading-container">
      <ion-spinner name="crescent"></ion-spinner>
      <p>{{ mensagemCarregamento }}</p>
    </div>

    <div v-else-if="existeErro" class="error-container">
      <ion-icon :icon="warningIcon" color="danger"></ion-icon>
      <p>{{ mensagemErro }}</p>
      <ion-button @click="tentarNovamente">Tentar novamente</ion-button>
    </div>

    <div v-else>
      <slot />
    </div>
  </div>
</template>

<script setup>
import { IonSpinner, IonIcon, IonButton } from '@ionic/vue';
import { warning as warningIcon } from 'ionicons/icons';

defineProps({
  existeCarregamento: {
    type: Boolean,
    default: false
  },
  existeErro: {
    type: Boolean,
    default: false
  },
  mensagemCarregamento: {
    type: String,
    default: 'Carregando informações'
  },
  mensagemErro: {
    type: String,
    default: 'Ocorreu um erro ao carregar as informações.'
  },
  tentarNovamente: {
    type: Function,
    required: true
  }
});
</script>

<style scoped>
.loading-container,
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 2rem;
  text-align: center;
}

p {
  margin-top: 0.5rem;
}
</style>
