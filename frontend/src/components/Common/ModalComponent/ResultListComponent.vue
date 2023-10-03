<script setup>
  import ItemComponent from './ItemComponent.vue';
  import ItemDetailComponent from './ItemDetailComponent.vue';
  import { defineProps, defineEmits, ref, onUpdated, onMounted } from 'vue';

  const selectedItem = ref("")
  const props = defineProps({
  partList : Object,
});

  const emit = defineEmits(['msg']);
  function select(item){
    if (item.name != selectedItem.value.name) {
      selectedItem.value = item
      const newItem = item
      emit('msg', newItem);
    }
    else{
      selectedItem.value = ""
      const newItem = {
        name : "", id: -1
      }
      emit('msg', newItem);
    }
  }
</script>

<template>
  <v-container class="container">
    <v-col class="leftSide"
    :cols=" selectedItem ? 8 : 12">
      <v-col
        v-for="item in partList"
        :key="item.id"
        >
          <ItemComponent
          :class="{ 'selected' : selectedItem.id == item.id }"
          :item = item
          @click="select(item)"/>
      </v-col>
    </v-col>
    <v-col
      cols="4"
      v-if="selectedItem">
      <ItemDetailComponent
      :selectedItem = "selectedItem"/>
    </v-col>
  </v-container>
</template>

<style scoped>

.selected {
  background: #4599FC;
  color: var(--Color, #FFF);

  font-family: Inter;
  font-size: 1.25rem;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
}
.container {
  /* margin-top : 5%; */
  width: 100%;
  flex-shrink: 0;
  flex-wrap : wrap;
  display: flex;
  align-items : center;
  justify-content : start;
  margin : 0 auto;
}

.leftSide {
  height: 70vh;
  overflow-y: scroll;
}
</style>
