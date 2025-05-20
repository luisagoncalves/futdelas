import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import SegmentView from '../views/SegmentView.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/segments'
  },
  {
    path: '/segments',
    component: SegmentView
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
