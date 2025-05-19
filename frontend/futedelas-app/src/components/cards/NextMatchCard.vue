<template>
  <ion-card>
    <ion-card-header>
      <ion-row class="ion-justify-content-between ion-align-items-center">
        <ion-card-title>{{ title }}</ion-card-title>

        <ion-buttons>
          <ion-button>
            <ion-icon slot="icon-only" :icon="icon" />
          </ion-button>
        </ion-buttons>
      </ion-row>
    </ion-card-header>

    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-text>{{ homeTeamName }}</ion-text>
              <ion-img
                :src="homeTeamLogo"
                style="width: 30px; height: 30px; margin-left: 8px;"
              />
            </ion-row>
          </ion-col>

          <ion-col class="ion-text-center">
            <ion-row class="ion-align-items-center ion-justify-content-center">
              <ion-img
                :src="awayTeamLogo"
                style="width: 30px; height: 30px; margin-right: 8px;"
              />
              <ion-text>{{ awayTeamName }}</ion-text>
            </ion-row>
          </ion-col>
        </ion-row>
      </ion-grid>

      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-text>{{ matchDateTime }}</ion-text>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
defineProps({
  title: String,
  homeTeamName: String,
  homeTeamLogo: String,
  awayTeamName: String,
  awayTeamLogo: String,
  matchDateTime: String,
  icon: Object // Ex: import { shareSocial } from 'ionicons/icons'
});
</script>
