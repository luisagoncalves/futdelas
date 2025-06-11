import { createApp } from 'vue'
import App from './App.vue'
import router from './router';

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

signInAnonymously(auth)
  .then(() => {
    console.log('Login bem-sucedido')
  })
  .catch((error) => {
    console.error("Erro na autenticação anônima:", error);
  });

onAuthStateChanged(auth, (user) => {
  if (user) {
    const uid = user.uid;
  } else {
    console.error("Usuário não logado");
  }
});
const app = createApp(App)
  .use(IonicVue)
  .use(router);

defineCustomElements(window);

router.isReady().then(() => {
  app.mount('#app');
});
