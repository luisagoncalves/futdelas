<template>
  <ion-segment-content id="third">
    <TimeCard 
      v-for="time in times"
      :key="time.time_id"
      :time-nome="time.nome_popular" 
      :time-escudo="time.escudo" 
    />
  </ion-segment-content>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getTimes } from '@/api/services/timeService';
import TimeCard from '@/components/cards/TimeCard.vue';
import { Time } from '@/api/interfaces/Time';

const times = ref<Time[]>([]);

onMounted(async () => {
  try {
    const response = await getTimes();
    times.value = response.map(time => ({
      ...time,
      timeFavorito: false
    }));
  } catch (error) {
    console.error('Erro ao carregar os times:', error);
  }
});
</script>
