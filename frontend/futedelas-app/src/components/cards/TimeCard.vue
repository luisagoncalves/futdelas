<template>
  <ion-card class="cartao-time" :class="{ 'time-favorito': isFavorito }">
    <ion-card-content>
      <ion-row class="ion-align-items-center ion-justify-content-between">
        <ion-row class="ion-align-items-center">
          <ion-avatar class="avatar-time">
            <ion-img
              :src="escudoTime"
              class="logo-time"
              alt="Escudo do time"
            ></ion-img>
          </ion-avatar>
          <ion-text class="nome-time">{{ nomeTime }}</ion-text>
        </ion-row>

        <ion-button 
          shape="round" 
          fill="clear"
          :disabled="!isFavorito && temOutroFavorito"
          @click="toggleFavorito()"
          class="botao-favorito">
          <ion-icon
            slot="icon-only"
            :icon="isFavorito ? heart : heartOutline"
            :color="isFavorito ? 'danger' : 'medium'"
          />
        </ion-button>
      </ion-row>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
import { IonButton, IonIcon, IonCard, IonCardContent, IonRow, IonAvatar, IonImg, IonText } from '@ionic/vue';
import { heart, heartOutline } from 'ionicons/icons'

const props = defineProps({
  nomeTime: {
    type: String,
    required: true
  },
  escudoTime: {
    type: String,
    required: true
  },
  timeId: {
    type: Number,
    required: true
  },
  isFavorito: {
    type: Boolean,
    default: false
  },
  temOutroFavorito: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['toggleFavorito']);

function toggleFavorito() {
  if (props.isFavorito || !props.temOutroFavorito) {
    emit('toggleFavorito', props.timeId);
  }
}
</script>

<style scoped>
.cartao-time {
  border-radius: 16px;
  padding: 12px;
  margin: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-left: 4px solid transparent;
}

.cartao-time:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.cartao-time.time-favorito {
  border-left: 4px solid var(--ion-color-danger);
  background-color: rgba(var(--ion-color-danger-rgb), 0.05);
}

.avatar-time {
  width: 48px;
  height: 48px;
  margin-right: 16px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-time {
  width: 30px;
  height: 30px;
}

.nome-time {
  font-weight: 600;
  font-size: 1.1rem;
  color: var(--ion-color-dark);
}

.icone-favorito {
  font-size: 28px;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.icone-favorito:hover:not(.desativado) {
  transform: scale(1.1);
}

.icone-favorito:active:not(.desativado) {
  transform: scale(0.95);
}

.icone-favorito.desativado {
  pointer-events: none;
  opacity: 0.4;
  cursor: not-allowed;
}

.botao-favorito {
  --color: inherit;
  --color-activated: inherit;
  --color-focused: inherit;
  --ripple-color: transparent;
}

.botao-favorito ion-icon {
  color: var(--ion-color-medium);
}

.botao-favorito ion-icon[color="danger"] {
  color: var(--ion-color-danger);
}
</style>