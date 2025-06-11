<template>
  <ion-card class="cartao-time" :class="{ 'time-favorito': timeFavorito }">
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

        <ion-icon
          :icon="timeFavorito ? heart : heartOutline"
          class="icone-favorito"
          :color="timeFavorito ? 'danger' : 'medium'"
          @click.stop="alternarFavorito"
        ></ion-icon>
      </ion-row>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
import { ref } from 'vue'
import { heart, heartOutline } from 'ionicons/icons'

const props = defineProps({
  nomeTime: String,
  escudoTime: String,
  isInitiallyFavorite: Boolean,
})

const timeFavorito = ref(props.isInitiallyFavorite)

function alternarFavorito() {
  timeFavorito.value = !timeFavorito.value
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
  width: 40px;
  height: 40px;
  margin-right: 16px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-time {
  width: 30px;
  height: 30px;
  object-fit: contain;
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

.icone-favorito:hover {
  transform: scale(1.1);
}

.icone-favorito:active {
  transform: scale(0.95);
}
</style>