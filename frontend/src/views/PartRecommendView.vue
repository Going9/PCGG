<template>
  <div class="container">
    <div class="selectone">
      <div class="nametag">
        <p>부품</p>
        <img :src="partIcon" alt="" />
      </div>
      <div class="category">
        <h3>부품종류</h3>
        <v-btn-toggle v-model="toggle" class="btns">
          <v-btn
            v-for="item in buttonItems"
            :key="item.value"
            :class="{
              'active-button': toggle === item.value,
              'inactive-button': toggle !== item.value,
            }"
            @click="setToggle(item.value)"
          >
            {{ item.label }}
          </v-btn>
        </v-btn-toggle>
      </div>
    </div>
    <div class="selecttwo">
      <div class="usage">
        <h3>용도</h3>
        <div class="usage-combo">
          <v-select
            v-for="(combo, index) in choiceUsage"
            :key="index"
            v-model="combo.selectedItem"
            :items="combo.items"
            :label="combo.label"
            variant="solo"
          ></v-select>
        </div>
      </div>
      <div class="budget">
        <h3>예산</h3>
        <v-text-field
          label=""
          variant="outlined"
          suffix="원"
          v-model="choiceBudget"
          type="number"
          step="10000"
          @input="filterNegativeNumbers"
        ></v-text-field>
      </div>

      <div class="priority">
        <h3>우선순위</h3>
        <div style="display: flex; align-items: center">
          <span style="font-weight: bold; color: #4599fc">가격</span>
          <v-slider
            v-model="priorityValue"
            thumb-label
            step="50"
            show-ticks
            track-color="#FC794F"
            track-fill-color="#4599fc"
            :thumb-color="color"
            :min="-100"
            :max="100"
            hide-details
          ></v-slider>
          <span style="font-weight: bold; color: #fc794f">성능</span>
        </div>
      </div>
      <div class="as">
        <h3>AS여부</h3>
        <div class="sub-select">
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceAs = !choiceAs"
            :class="{ active: choiceAs }"
          >
            <div class="as-value">필수</div>
          </V-btn>
        </div>
      </div>
    </div>

    <div class="result">
      <v-btn color="#DA0000" class="callrecommend" @click="addEstimate()">
        추천받기</v-btn
      >
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { partIcon } from "@/assets/Icon";
import { usePartRecommendStore } from "@/store/partRecommendStore";

const store = usePartRecommendStore();

const toggle = ref(null);

const setToggle = (value) => {
  toggle.value = value;
};

const buttonItems = [
  { label: "CPU", value: "cpu" },
  { label: "쿨러", value: "cooler" },
  { label: "메인보드", value: "mainboard" },
  { label: "메모리", value: "memory" },
  { label: "GPU", value: "gpu" },
  { label: "SSD", value: "ssd" },
  { label: "케이스", value: "case" },
  { label: "파워", value: "power" },
];

const choiceUsage = ref([
  {
    selectedItem: null,
    items: [
      "가성비사무",
      "고성능사무",
      "캐주얼게임",
      "중사양게임",
      "고사양게임",
      "일반영상편집",
      "전문영상편집",
      "3D디자인",
      "캐주얼게임방송",
      "고성능게임방송",
      "고사양개발",
    ],
    label: "용도",
  },
]);

const choiceBudget = ref("");

const filterNegativeNumbers = () => {
  if (choiceBudget.value < 0) {
    choiceBudget.value = 0;
  }
};

const budgetValue = computed(() => {
  return Number(choiceBudget.value);
});

const priorityValue = ref(0);

const color = computed(() => {
  if (priorityValue.value > 0) return "#FC794F";
  if (priorityValue.value < 0) return "#4599fc";
  return "white";
});

const choiceAs = ref(null);

const estimate = ref([]);

const addEstimate = () => {
  estimate.value.push(toggle.value);

  choiceUsage.value.forEach((combo) => {
    if (combo.selectedItem) {
      estimate.value.push(combo.selectedItem);
    }
  });

  estimate.value.push(budgetValue.value);

  estimate.value.push(priorityValue.value);

  if (choiceAs.value) {
    estimate.value.push(true);
  } else {
    estimate.value.push(false);
  }

  if (estimate.value[1] != 0) {
    store.callPart(estimate.value);
  }

  console.log(estimate.value);
  estimate.value = [];
};
</script>

<style scoped>
::v-deep(.v-field__append-inner) {
  display: none;
}

.container {
  margin-top: 2rem;
}

.selectone {
  display: flex;
  margin: 2%;
  justify-content: space-between;
}

.nametag {
  width: 150px;
  height: 150px;
  flex-shrink: 0;
  border-radius: 10px;
  background: var(--Color, #fff);
  box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.25);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.nametag p {
  color: #000;
  font-family: Inter;
  font-size: 20px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  margin-top: 21px;
}

.nametag img {
  width: 61px;
  height: 58px;
  margin-top: 18px;
}

.main-select {
  display: flex;
  width: 25%;
  margin-right: 2%;
  justify-content: space-between;
}
.btn-main {
  display: flex;
  height: 10rem !important;
  width: 10rem;
  box-shadow: 2px 2px 5px #00000050;
}

.btn-main.active {
  background-color: #4599fc;
  color: white;
  box-shadow: inset 2px 2px 5px #00000050;
}

.btn-main p {
  font-size: large;
  font-weight: bolder;
}

.btn-main img {
  height: 2rem;
}

.sub-select {
  display: flex;
  justify-content: space-evenly;
}

.category {
  width: 100%;
  margin-left: 2%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  padding: 0 0 2% 1%;
}

.btns {
  display: flex;
  justify-content: space-evenly;
}

.active-button {
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
  color: #fff;
  background: #d9d9d9;
  border-radius: 30px !important;
  text-shadow: 2px 2px 5px #00000080;
  height: 50px;
  width: 12%;
  background-color: #4599fc;
  box-shadow: inset 2px 2px 5px #00000080;
}

.inactive-button {
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
  color: #fff;
  background: #d9d9d9;
  border-radius: 30px !important;
  height: 50px;
  width: 12%;
}

.cleanbtn {
  margin-right: 1%;
}

.btn-sub {
  display: flex;
  height: 7rem !important;
  width: 7rem;
  box-shadow: 2px 2px 5px #00000050;
}

.btn-sub.active {
  background-color: #4599fc;
  color: white;
  box-shadow: inset 2px 2px 5px #00000050;
}

.btn-sub p {
  font-weight: bolder;
  margin-bottom: 1rem;
}

.btn-sub img {
  height: 2rem;
}

.selecttwo {
  margin: 2%;
  display: flex;
  justify-content: space-evenly;
}

.usage {
  width: 25%;
  padding-right: 2%;
}

.usage-combo {
  display: flex;
  height: 100%;
}

.budget {
  width: 25%;
  padding-right: 2%;
}

.os {
  width: 25%;
  padding-right: 2%;
}

.priority {
  width: 25%;
  padding-right: 2%;
}

.as {
  width: 15%;
  padding-right: 2%;
}

.as-value {
  font-size: x-large;
  font-weight: bold;
}

.case {
  width: 15%;
  padding-right: 2%;
}

.ssd {
  width: 15%;
  padding-right: 2%;
}

.result {
  margin: 0% 2%;
}

.callrecommend {
  height: 4rem !important;
  width: 100%;
  font-size: large;
  font-weight: bolder;
}
</style>
