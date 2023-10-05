<template>
  <div class="container">
    <img :src="bestsellerImg" class="outerImg" alt="bestSeller" />
    <div class="innerImg">
      <PostComponent
      style="{{ color = black; }}"
      :post="props.item"
      v-if="props.item"/>
    </div>
  </div>
</template>

<script setup>
import {defineProps} from 'vue'
import PostComponent from "@/components/ShareViewComponents/PostComponent.vue";
import { bestsellerImg } from "@/assets/image";
import { laptopImg } from "@/assets/image";

const props = defineProps({
  item: Object
});
</script>

<style scoped>
.container {
  margin-top: 5%;
  height: 88%;
  width: 88%;
  color: black;
  position: relative;
}

.outerImg {
  position: relative;
  width: 110%;
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
