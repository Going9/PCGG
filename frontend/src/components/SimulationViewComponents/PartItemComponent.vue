<template>
  <div>
    <div class="partlist" v-for="(item, index) in templist" :key="index">
      <img :src="item" alt="noImage" class="partimg" />
      <div class="partsummary">
        <p>제품이름 : {{ store.partCategory }}</p>
        <p>가격 : 100원</p>
        <p>제품사양 : 구림</p>
      </div>
      <v-btn variant="tonal" class="simulation">선택</v-btn>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUpdated, ref } from "vue";
import { cpuImgOne } from "@/assets/image";
import { cpuImgTwo } from "@/assets/image";
import { useSimulationStore } from "@/store/simulationStore";

const store = useSimulationStore();

const templist = [
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
  cpuImgOne,
  cpuImgTwo,
];

const page = ref(0);

const callRecommend = () => {
  console.log("call Recommend");
};

const callList = () => {
  console.log("call List");
};

onMounted(() => {
  page.value = 0;
  callRecommend();
  callList();
});

onUpdated(() => {
  page.value = 0;
  callRecommend();
  callList();
});
</script>

<style scoped>
.partlist {
  margin: 1rem;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.partimg {
  height: 5rem;
  border-radius: 5px;
}

.partsummary {
  border: solid 1px #000000;
  border-radius: 5px;
  width: 60%;
}
</style>
