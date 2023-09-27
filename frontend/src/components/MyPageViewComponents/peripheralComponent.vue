<template>
  <div class="button-container">
    <v-btn-toggle v-model="category" class="buttons">
      <v-btn
        v-for="item in buttonItems"
        :key="item.value"
        :value="item.value"
        :class="{
          'active-button': category === item.value,
          'inactive-button': category !== item.value,
        }"
        @click="buttonEvent(item.value)"
      >
        {{ item.label }}
      </v-btn>
    </v-btn-toggle>
  </div>
</template>

<script setup>
import { userStore } from "@/store/userStore";
import { onMounted, ref } from "vue";

const store = userStore();
const category = ref(null);
const buttonItems = [
  { label: "키보드", value: "keyboard" },
  { label: "모니터", value: "monitor" },
  { label: "마우스", value: "mouse" },
  { label: "프린터/복합기", value: "printer" },
];

onMounted(() => {
  category.value = store.getPeripheralCategory;
});

const buttonEvent = async (value) => {
  category.value = value;
  await store.setCategory(value);
  await store.getMyPeripheral(value);
  console.log(store.getperipheralList);
};
</script>

<style scoped>
.button-container {
  width: 1200px;
  position: relative;
  margin: auto;
  margin-top: 50px;
}
.buttons {
  display: flex;
  justify-content: space-between;
}
.active-button {
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
  color: #fff;
  background: #d9d9d9;
  border-radius: 20px !important;
  text-shadow: 2px 2px 5px #00000080;
  height: 50px;
  width: 18%;
  background-color: #4599fc;
  box-shadow: inset 2px 2px 5px #00000080;
}
.inactive-button {
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
  color: #fff;
  background: #d9d9d9;
  border-radius: 20px !important;
  height: 50px;
  width: 18%;
}
</style>
