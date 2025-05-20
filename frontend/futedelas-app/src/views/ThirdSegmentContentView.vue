<template>
  <ion-segment-content id="third">
    <TeamCard 
      :team-name="team.name" 
      :team-logo="team.logo" 
      :is-initially-favorite="team.isFavorite"
      @toggle-favorite="toggleFavorite" 
    />
  </ion-segment-content>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import TeamCard from '@/components/cards/TeamCard.vue';

// Dados reativos do time
const team = ref({
  name: "Santos",
  logo: "src/assets/images/santos.png",
  isFavorite: false
});

// Função para alternar o status de favorito
const toggleFavorite = () => {
  team.value.isFavorite = !team.value.isFavorite;
  console.log(`Time ${team.value.name} agora é ${team.value.isFavorite ? 'favorito' : 'não favorito'}`);
};
</script>