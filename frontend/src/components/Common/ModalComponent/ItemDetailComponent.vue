<script setup>
  import {defineProps} from 'vue'
  const { selectedItem } = defineProps(['selectedItem']);
</script>

<template>
  <v-container>
    <v-row>
      <img
      class="part-img"
      :src="selectedItem.image_source" alt="PartImg">
    </v-row>
    <v-row>
      <div class="part-info">
        <v-col cols="12" class="ml-3">
          <h3>
            제품명 : {{ selectedItem.name }}
          </h3>
        </v-col>
        <v-col cols="12" class="ml-3">
          <h3>
            가 격 : {{ selectedItem.price }}원
          </h3>
        </v-col>
      </div>
    </v-row>
  </v-container>
</template>

<style scoped>
 .part-img {
  width: 100%;
  height: 50%;
 }

 .part-info {
  width: 100%;
  height: 25%;
  border-radius: 0.625rem;
  border: 1px solid #000;
  text-align: start;
 }
</style>
