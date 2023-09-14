<template>
  <div>
    <div
      v-for="(card, index) in temps"
      :key="index"
      class="cardform"
      @click="goDetail"
    >
      <img :src="card" alt="Card Image" />
      <div class="card-body">
        <h3 class="card-title">매우 비싼 {{ store.peripheralCategory }}</h3>
        <p class="card-text">가성비 엉망</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onUpdated, ref } from "vue";
import router from "@/router";
import temp1 from "@/assets/temp1.png";
import { useAppStore } from "@/store/app";

const store = useAppStore();

const goDetail = () => {
  router.push({ name: "PeripheralDetail" });
};

const temps = [
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
  temp1,
];

const page = ref(1);

const callRecommend = () => {
  console.log("call Recommend");
  store.isRecommendPreipheral(store.peripheralCategory);
};

const callList = () => {
  console.log("call List");
  store.isPeripheralList(store.peripheralCategory, page.value);
  page.value += 1;
};

onUpdated(() => {
  callRecommend();
  callList();
});
</script>

<style scoped>
.cardform {
  width: 17.3%;
  margin: 0% 1.3% 2% 1.3%;
  border-radius: 10px;
  background-color: #ffffff9a;
  flex: none;
  transition: transform 0.2s ease;
}

.cardform:hover {
  transform: scale(1.1);
}

.cardform img {
  border-radius: 10px 10px 0px 0px;
  width: 100%;
}

.card-body {
  margin: 5px 5px 0px 5px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-body p {
  text-align: right;
}
</style>
