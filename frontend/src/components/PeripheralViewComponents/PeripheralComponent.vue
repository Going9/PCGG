<template>
  <div class="container">
    <div class="text">
      <span class="username">username</span><span>님께 추천드리는 </span>
      <span v-if="store.peripheralCategory === 'keyboard'">키보드</span>
      <span v-if="store.peripheralCategory === 'mouse'">마우스</span>
      <span v-if="store.peripheralCategory === 'monitor'">모니터</span>
      <span v-if="store.peripheralCategory === 'printer'">프린터와 복합기</span>
      <span v-if="store.peripheralCategory === 'etc'">주변기기</span>
    </div>
    <PeripheralCardComponentVue class="recommend" :categoryVariable="toggle" />
    <!-- 분리선 -->
    <div class="text">
      <div class="all-products">
        <div class="products-list">전체상품</div>
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
          >
            {{ item.label }}
          </v-btn>
        </v-btn-toggle>
      </div>
      <PeripheralCardComponentVue class="list" :categoryVariable="toggle" />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { usePeripehralStore } from "@/store/peripheralStore";
import PeripheralCardComponentVue from "./PeripheralCardComponent.vue";

const store = usePeripehralStore();

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
  height: 100%;
  width: 90%;
  padding: 5px;
  background-color: #d9d9d925;
  flex-direction: column;
  border-radius: 10px;
}

.text {
  margin: 5px;
}

.username {
  font-size: x-large;
}

.recommend {
  padding-top: 2%;
  display: flex;
  overflow-x: auto;
}

.recommend::-webkit-scrollbar {
  height: 10px;
  background-color: #d9d9d9;
}

.recommend::-webkit-scrollbar-thumb {
  background-color: #4599fc;
  border-radius: 15px;
}

.all-products {
  margin: 5px 0px 10px 0px;
  display: flex;
  justify-content: space-between;
}

.products-list {
  display: flex;
  justify-content: center; /* 텍스트를 수평으로 가운데 정렬 */
  align-items: center; /* 텍스트를 수직으로 가운데 정렬 */
  font-weight: bold;
}

.list {
  display: flex;
  flex-wrap: wrap;
}
</style>
