<template>
  <ion-card class="team-card">
    <ion-card-content>
      <ion-row class="ion-align-items-center ion-justify-content-between">
        <ion-row class="ion-align-items-center">
          <ion-img
            :src="teamLogo"
            class="team-logo"
          ></ion-img>
          <ion-text class="team-name">{{ teamName }}</ion-text>
        </ion-row>

        <ion-icon
          :icon="isFavorite ? heart : heartOutline"
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
  teamName: String,
  teamLogo: String,
  isInitiallyFavorite: Boolean,
})

const isFavorite = ref(props.isInitiallyFavorite)

function toggleFavorite() {
  isFavorite.value = !isFavorite.value
  // Você pode emitir um evento aqui, ex: emit('favoriteToggled', isFavorite.value)
}
</script>

<style scoped>
.team-card {
  border-radius: 16px;
  --background: #2d2d2d; /* cor escura de fundo, como no exemplo */
  padding: 8px;
}

.team-logo {
  width: 32px;
  height: 32px;
  margin-right: 12px;
}

.team-name {
  font-size: 16px;
  color: white;
}

.favorite-icon {
  font-size: 24px;
}
</style>
