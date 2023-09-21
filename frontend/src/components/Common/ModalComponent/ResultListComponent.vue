<script setup>
  import ItemComponent from './ItemComponent.vue';
</script>

<template>
  <v-container class="container">
    <v-col cols="12" v-for="num in 8"
         :key="num">
        <ItemComponent/>
    </v-col>
  </v-container>
</template>

<style scoped>
.container {
  /* margin-top : 5%; */
  width: 100%;
  flex-shrink: 0;
  flex-wrap : wrap;
  display: flex;
  align-items : center;
  justify-content : start;
}

</style>
