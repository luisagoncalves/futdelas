<template>
  <ion-card class="time-card">
    <ion-card-content>
      <ion-row class="ion-align-items-center ion-justify-content-between">
        <ion-row class="ion-align-items-center">
          <ion-img
            :src="timeEscudo"
            class="time-logo"
          ></ion-img>
          <ion-text>{{ timeNome }}</ion-text>
        </ion-row>

        <ion-icon
          :icon="timeFavorito ? heart : heartOutline"
          class="favorite-icon"
          color="danger"
          @click="toggleFavorite"
        ></ion-icon>
      </ion-row>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
import { ref } from 'vue'
import { heart, heartOutline } from 'ionicons/icons'

const props = defineProps({
  timeNome: String,
  timeEscudo: String,
  isInitiallyFavorite: Boolean,
})

const timeFavorito = ref(props.isInitiallyFavorite)

function toggleFavorite() {
  timeFavorito.value = !timeFavorito.value
}
</script>

<style scoped>
.time-card {
  border-radius: 16px;
  padding: 8px;
}

.time-logo {
  width: 32px;
  height: 32px;
  margin-right: 12px;
}

.favorite-icon {
  font-size: 24px;
}
</style>
