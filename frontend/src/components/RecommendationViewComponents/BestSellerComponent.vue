<template>
  <div class="container">
    <img :src="bestsellerImg" class="outerImg" alt="bestSeller" />
    <div class="innerImg">
      <img
        :src="laptopImg"
        alt="laptopImg"
        style="{{ width: 80%; height: 80%; }}"
      />
    </div>
  </div>
</template>

<script setup>
import { bestsellerImg } from "@/assets/image";
import { laptopImg } from "@/assets/image";
</script>

<style scoped>
.container {
  margin-top: 5%;
  height: 88%;
  width: 88%;
  color: white;
  position: relative;
}

.outerImg {
  position: relative;
  width: 100%;
  height: 100%;
}
.innerImg {
  top: 20%;
  left: 33%;
  position: absolute;
  width: 60%;
  height: 60%;
}
</style>
