<template>
  <div class="container">
    <img :src="simulationbannerImg" alt="" />
  </div>
</template>

<script setup>
import { simulationbannerImg } from "@/assets/image";
</script>

<style scoped>
.container {
  height: 100%;
  width: 100%;
}

.container img {
  height: 20rem;
  width: 100vw;
}
</style>
