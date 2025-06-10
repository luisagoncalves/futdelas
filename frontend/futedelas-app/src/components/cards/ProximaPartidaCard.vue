<template>
  <ion-card>
    <ion-card-header>
      <ion-row class="ion-justify-content-between ion-align-items-center">
        <ion-card-title>Próxima partida</ion-card-title>

        <ion-buttons>
          <ion-button>
            <ion-icon slot="icon-only" :icon="icone" />
          </ion-button>
        </ion-buttons>
      </ion-row>
    </ion-card-header>

    <ion-card-content>
     <!-- Estado de carregamento -->
      <div v-if="loading" class="loading-container">
        <ion-spinner name="crescent"></ion-spinner>
        <p>Carregando classificação...</p>
      </div>

      <!-- Estado de erro -->
      <div v-else-if="error" class="error-container">
        <ion-icon :icon="warning" color="danger"></ion-icon>
        <p>Erro ao carregar informações</p>
        <ion-button @click="buscarProximaPartida">Tentar novamente</ion-button>
      </div>

      <ion-grid v-else>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-text>{{ timeMandante }}</ion-text>
              <ion-img
                :src="timeMandanteEscudo"
                style="width: 30px; height: 30px; margin-left: 8px;"
              />
            </ion-row>
          </ion-col>

          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-img
                :src="timeVisitanteEscudo"
                style="width: 30px; height: 30px; margin-right: 8px;"
              />
              <ion-text>{{ timeVisitante }}</ion-text>
            </ion-row>
          </ion-col>
        </ion-row>
      </ion-grid>

      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-text>{{ horario }}</ion-text>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
defineProps({
  data: String,
  timeMandante: String,
  timeMandanteEscudo: String,
  timeVisitante: String,
  timeVisitanteEscudo: String,
  horario: String,
  icone: Object
});
</script>

<style scoped>
.loading-container,
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  gap: 12px;
}

.error-container ion-icon {
  font-size: 48px;
}
</style>
