<template>
  <AppHeader />
  <AppSegment />

  <ion-segment-view>
    <FirstSegmentContentView />
    <SecondSegmentContentView />
    <ThirdSegmentContentView />
  </ion-segment-view>
</template>


<script setup lang="ts">
import AppHeader from '@/components/layout/AppHeader.vue'
import AppSegment from '@/components/layout/AppSegment.vue'
import FirstSegmentContentView from './FirstSegmentContentView.vue'
import SecondSegmentContentView from './SecondSegmentContentView.vue'
import ThirdSegmentContentView from './ThirdSegmentContentView.vue'

</script>
