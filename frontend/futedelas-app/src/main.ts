import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
import { createPinia } from 'pinia'

import { IonicVue } from '@ionic/vue';
import '@ionic/vue/css/core.css';

import '@ionic/vue/css/normalize.css';
import '@ionic/vue/css/structure.css';
import '@ionic/vue/css/typography.css';

import '@ionic/vue/css/padding.css';
import '@ionic/vue/css/float-elements.css';
import '@ionic/vue/css/text-alignment.css';
import '@ionic/vue/css/text-transformation.css';
import '@ionic/vue/css/flex-utils.css';
import '@ionic/vue/css/display.css';

import './theme/variables.css';
import { defineCustomElements } from '@ionic/pwa-elements/loader';
import { getAuth, onAuthStateChanged, signInAnonymously } from "firebase/auth";

const auth = getAuth();

function initAnonymousAuth() {
  signInAnonymously(auth)
    .then(() => {
      console.log('Login anônimo bem-sucedido');
    })
    .catch((error) => {
      console.error("Erro na autenticação anônima:", error);
    });
}

onAuthStateChanged(auth, (user) => {
  if (user) {
    const uid = user.uid;
    localStorage.setItem('anonymousUserId', uid);
    console.log('UID do usuário anônimo:', uid);
  } else {
    console.log('Usuário não autenticado');
    initAnonymousAuth();
  }
});

initAnonymousAuth();
const pinia = createPinia();

const app = createApp(App)
  .use(IonicVue)
  .use(router)
  .use(pinia);

defineCustomElements(window);

router.isReady().then(() => {
  app.mount('#app');
});
