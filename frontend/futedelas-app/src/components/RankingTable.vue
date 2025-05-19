<template>
  <ion-card>
    <ion-card-header>
      <ion-card-title>Classificação</ion-card-title>
    </ion-card-header>

    <ion-card-content>
      <ion-grid>
        <!-- Cabeçalho da tabela -->
        <ion-row class="header-row">
          <ion-col size="5">Equipe</ion-col>
          <ion-col size="1" class="ion-text-center">J</ion-col>
          <ion-col size="1" class="ion-text-center">V</ion-col>
          <ion-col size="1" class="ion-text-center">E</ion-col>
          <ion-col size="1" class="ion-text-center">D</ion-col>
          <ion-col size="2" class="ion-text-center">PTS</ion-col>
        </ion-row>

        <!-- Times da tabela -->
        <ion-row
          v-for="(team, index) in teams"
          :key="index"
          :class="index % 2 === 0 ? 'even-row' : 'odd-row'"
        >
          <ion-col size="5" class="ion-align-items-center">
            <div class="position-label" v-if="index < 8">{{ index + 1 }}</div>
            <div class="team-name">{{ team.name }}</div>
          </ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.j }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.v }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.e }}</ion-col>
          <ion-col size="1" class="ion-text-center">{{ team.d }}</ion-col>
          <ion-col size="2" class="ion-text-center">{{ team.pts }}</ion-col>
        </ion-row>
      </ion-grid>
    </ion-card-content>
  </ion-card>
</template>

<script setup>
const teams = [
  { name: 'Cruzeiro', j: 10, v: 8, e: 1, d: 1, pts: 25 },
  { name: 'Ferroviária', j: 10, v: 7, e: 2, d: 1, pts: 23 },
  { name: 'Palmeiras', j: 10, v: 7, e: 1, d: 2, pts: 22 },
  { name: 'Corinthians', j: 10, v: 6, e: 2, d: 2, pts: 20 },
  { name: 'São Paulo', j: 10, v: 6, e: 1, d: 3, pts: 19 },
  { name: 'Flamengo', j: 10, v: 5, e: 2, d: 3, pts: 17 },
  { name: 'América-MG', j: 10, v: 5, e: 1, d: 4, pts: 16 },
  { name: 'Bragantino', j: 10, v: 4, e: 3, d: 3, pts: 15 },
  { name: 'Bahia', j: 10, v: 4, e: 1, d: 5, pts: 13 },
  { name: 'Fluminense', j: 10, v: 3, e: 2, d: 5, pts: 11 }
]
</script>

<style scoped>
.header-row {
  font-weight: bold;
  background-color: #1e1e1e;
  color: white;
  padding: 8px 0;
}

.even-row {
  background-color: #2d2d2d;
}

.odd-row {
  background-color: #1c1c1c;
}

.position-label {
  display: inline-block;
  background-color: #0ac144;
  color: white;
  border-radius: 50%;
  width: 22px;
  height: 22px;
  text-align: center;
  line-height: 22px;
  font-size: 12px;
  margin-right: 6px;
}

.team-name {
  display: inline-block;
  vertical-align: middle;
}
</style>
