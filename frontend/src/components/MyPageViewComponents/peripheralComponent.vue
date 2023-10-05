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
  <div if="peripheralList.length == 0" v-for="item in buttonItems" :key="item">
    <div class="non-item-text" v-if="category === item.value">
      저장한 {{ item.label }}가 없습니다.
    </div>
  </div>
  <div
    class="peripheral-container"
    v-for="peripheral in peripheralList"
    :key="peripheral"
  >
    <img
      :src="peripheral['imageSource']"
      alt="noimage!"
      class="peripheralimg"
    />
    <v-divider class="border-opacity-100" vertical />
    <div class="peripherals">
      <div class="peripheral">
        <div>
          <p>제품명: {{ peripheral["name"] }}</p>
        </div>
        <div class="peripheral">
          <p>최고가: {{ peripheral["hprice"] }}</p>
        </div>
        <div class="peripheral">
          <p>최저가: {{ peripheral["lprice"] }}</p>
        </div>
      </div>
      <div class="peripheral">
        <div>
          <p>브랜드: {{ peripheral["brand"] }}</p>
        </div>
        <div>
          <v-btn :href="peripheral['link']">상품 페이지</v-btn>
        </div>
      </div>
    </div>
    <v-divider class="border-opacity-100" vertical />
    <v-btn
      icon="$vuetify"
      class="peripheralbtn"
      variant="text"
      @click="removeMyPeripheral(peripheral['id'])"
    >
      <img :src="removeIcon" alt="no" class="remove" />
    </v-btn>
  </div>
</template>

<script setup>
import { userStore } from "@/store/userStore";
import { onMounted, computed, ref } from "vue";
import { removeIcon } from "@/assets/Icon";

const store = userStore();
const category = ref(null);
const buttonItems = [
  { label: "키보드", value: "keyboard" },
  { label: "모니터", value: "monitor" },
  { label: "마우스", value: "mouse" },
  { label: "프린터/복합기", value: "printer" },
];
const peripheralList = computed(function () {
  return store.getperipheralList;
});

onMounted(() => {
  category.value = store.getPeripheralCategory;
  store.getMyPeripheral(category.value);
});

const buttonEvent = async (value) => {
  category.value = value;
  await store.setPeripheralCategory(value);
  await store.getMyPeripheral(value);
  // console.log(store.getperipheralList);
};

const removeMyPeripheral = async (peripheralId) => {
  await store.deleteMyPeripheral(peripheralId);
  await store.getMyPeripheral(category.value);
};
</script>

<style scoped>
.button-container {
  width: 1200px;
  position: relative;
  margin: auto;
  margin-top: 50px;
}
.non-item-text {
  font-weight: bold;
  text-align: center;
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
.peripheral-container {
  display: flex;
  margin: 2rem 3rem 0rem 3rem;
  align-items: center;
  background-color: #d9d9d9;
  border-radius: 10px;
  padding: 1rem;
  transition: border-radius 0.3s ease-in-out;
}
.peripheralimg {
  height: 5rem;
  margin-right: 3rem;
}
.peripherals {
  display: flex;
  padding: 3px 1rem;
  width: 80%;
  cursor: pointer;
  justify-content: space-between;
}
.peripheral {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-right: 2rem;
}
.peripheralbtn {
  margin-left: 3rem;
}
.remove {
  width: 100%;
}
</style>
