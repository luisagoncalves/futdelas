<template>
  <ion-segment-content id="third">
    <TeamCard 
      v-for="team in teams"
      :key="team.time_id"
      :team-name="team.nome_popular" 
      :team-logo="team.escudo" 
    />
  </ion-segment-content>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getTimes } from '@/api/services/timeService';
import TeamCard from '@/components/cards/TeamCard.vue';
import { Time } from '@/api/interfaces/Time';

const teams = ref<Time[]>([]);

onMounted(async () => {
  try {
    const response = await getTimes();
    // Supondo que o backend não traga o campo isFavorite, adicionamos manualmente:
    teams.value = response.map(team => ({
      ...team,
      isFavorite: false // ou buscar de um storage/localStorage se quiser persistir
    }));
  } catch (error) {
    console.error('Erro ao carregar os times:', error);
  }
});
</script>
