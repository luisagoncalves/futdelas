import { defineStore } from 'pinia'
import { ref } from 'vue'
import { buscarTimeFavorito } from '@/api/services/timeFavoritoService'
import { getCurrentUserId } from '@/api/services/auth'

export const useStoreTimeFavorito = defineStore('timeFavoritoStore', () => {
  const idTimeFavorito = ref<number | null>(null)
  
  async function carregarTimeFavorito() {
    try {
      const idUsuario = getCurrentUserId()
      const favorito = await buscarTimeFavorito(idUsuario)
      idTimeFavorito.value = favorito?.timeId || null
      return idTimeFavorito.value
    } catch (erro) {
      console.error('Erro ao buscar time favorito:', erro)
      idTimeFavorito.value = null
      return null
    }
  }

  const temTimeFavorito = () => idTimeFavorito.value !== null

  carregarTimeFavorito()

  return {
    idTimeFavorito,
    temTimeFavorito,
    carregarTimeFavorito
  }
})