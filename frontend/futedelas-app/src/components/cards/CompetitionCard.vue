<template>
  <ion-card>
    <ion-card-header>
      <ion-card-title>{{ title }}</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <ion-grid>
        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-row class="ion-justify-content-center ion-align-items-center">
              <ion-img :src="teamLogo" alt="Logo do time" style="width: 40px; height: 40px;"></ion-img>
            </ion-row>
            <ion-row class="ion-justify-content-center ion-align-items-center" style="flex-direction: column;">
              <ion-text>{{ teamName }}</ion-text>
              <ion-text>Atual campeão</ion-text>
            </ion-row>
          </ion-col>
        </ion-row>

        <ion-row class="ion-justify-content-center ion-align-items-center">
          <ion-col class="ion-text-center">
            <ion-text>{{ teamsCount }}</ion-text>
          </ion-col>
          <ion-col class="ion-text-center">
            <ion-text>{{ roundsInfo }}</ion-text>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
defineProps({
  title: String,
  teamName: String,
  teamLogo: String,
  teamsCount: String,
  roundsInfo: String
});
</script>
