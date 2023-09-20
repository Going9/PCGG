<template>
  <div class="container">
    <div class="sortmenu">
      <v-btn-toggle
        v-model="toggle"
        size="small"
        density="compact"
        color="primary"
        variant="plain"
      >
        <v-btn
          v-for="item in buttonItems"
          :key="item.value"
          :value="item.value"
          @click="setToggle(item.value)"
          height="1rem"
          width="5.5rem"
        >
          {{ item.label }}
        </v-btn>
      </v-btn-toggle>
    </div>
    <PartItemComponentVue />
  </div>
</template>

<script setup>
import { ref } from "vue";
import PartItemComponentVue from "./PartItemComponent.vue";

const toggle = ref(null);

const setToggle = (value) => {
  toggle.value = value;
  console.log(toggle);
};

const buttonItems = [
  { label: "인기상품순", value: "popularity" },
  { label: "신상품순", value: "new" },
  { label: "낮은가격순", value: "low" },
  { label: "높은가격순", value: "high" },
  { label: "상품명순", value: "name" },
];
</script>

<style scoped>
.container {
  width: 80% !important;
  background-color: #d9d9d925;
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-radius: 5px;
}

.sortmenu {
  height: 1rem;
  display: flex;
  justify-content: right;
  margin-top: 0.5rem;
}
</style>
