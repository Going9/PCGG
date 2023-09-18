<template>
  <div class="container">
    <div class="banner">
      <img :src="simulationbannerImg" alt="" />
      <div class="banner-text">
        <p>시뮬레이션에서는 부품간의 호환성을</p>
        <p>확인할 수 있습니다</p>
      </div>
    </div>

    <div class="partbar">
      <div class="part">
        <v-btn-toggle v-model="toggle" class="btns">
          <v-btn
            v-for="item in buttonItems"
            :key="item.value"
            :value="item.value"
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
      <v-btn icon="$vuetify" variant="tonal">
        <img :src="saveIcon" alt="save" class="saveIcon" />
      </v-btn>
      <div>
        <v-btn variant="tonal" class="simulation">시뮬레이션</v-btn>
      </div>
    </div>

    <div class="searcharea">
      <div class="searchbar">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="검색"
          id="searchbar-inner"
          @keyup.enter="goSearch"
          class="searchbar-inner"
        />
        <img
          :src="searchIcon"
          alt="돋보기"
          @click="goSearch"
          class="searchmark"
        />
      </div>
    </div>

    <div class="partlist">
      <div>temp</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { simulationbannerImg } from "@/assets/image";
import { saveIcon } from "@/assets/Icon";
import { searchIcon } from "@/assets/Icon";
import { useAppStore } from "@/store/app";

const store = useAppStore();

const toggle = ref(null);

const setToggle = (value) => {
  toggle.value = value;
  store.isPeripheralCategory(value);
  console.log(store.peripheralCategory);
};

const buttonItems = [
  { label: "CPU", value: "CPU" },
  { label: "쿨러", value: "COOLER" },
  { label: "메인보드", value: "MAINBOARD" },
  { label: "메모리", value: "MEMORY" },
  { label: "GPU", value: "GPU" },
  { label: "SSD", value: "SSD" },
  { label: "HDD", value: "HDD" },
  { label: "케이스", value: "CASE" },
  { label: "파워", value: "POWER" },
];

const searchQuery = ref("");

const goSearch = () => {
  // 검색을 수행하는 로직을 여기에 추가
  console.log("검색어:", searchQuery.value);

  // 검색 후 입력 내용 초기화
  searchQuery.value = "";
};
</script>

<style scoped>
.container {
  height: 100%;
  width: 100%;
}

.banner {
  position: relative; /* 부모 요소에 상대 위치 설정 */
  height: 15rem;
  width: 100vw;
}

.banner img {
  height: 20rem;
  width: 100%;
}

.banner-text {
  position: absolute;
  bottom: 23%;
  left: 0;
  padding: 10px;
  width: 100%;
  text-align: center;
  color: #ffffff;
  font-size: 2rem;
  font-weight: bold;
}

.banner p {
  margin: 0;
  text-shadow: 2px 2px 5px #00000080;
}

.partbar {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #4599fc40;
  height: 5rem;
  z-index: 2;
}

.part {
  display: flex;
  justify-content: space-between;
  width: 80%;
}

.saveIcon {
  height: 1rem;
  margin: 0% 3% 0% 3%;
}

.btns {
  display: flex;
  justify-content: space-evenly;
  width: 100%;
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
  width: 10%;
  background-color: #4599fc;
  box-shadow: inset 2px 2px 5px #00000080;
}

.inactive-button {
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
  color: #fff;
  background: #cbcbcb;
  border-radius: 30px !important;
  height: 50px;
  width: 10%;
}

.simulation {
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
  color: #fff;
  background: #cbcbcb;
  border-radius: 30px !important;
  height: 50px;
  width: 100%;
}

.simulation:active {
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
  color: #fff;
  background: #d9d9d9;
  border-radius: 30px !important;
  text-shadow: 2px 2px 5px #00000080;
  height: 50px;
  width: 100%;
  background-color: #4599fc;
  box-shadow: inset 2px 2px 5px #00000080;
}

.searcharea {
  width: 100%;
  display: flex;
  justify-content: center;
  margin: 2%;
}

.searchbar {
  margin: auto;
  margin-top: 0px;
  margin-bottom: 0px;
  width: 50%;
  position: relative;
  display: inline-block;
}

.searchbar-inner {
  box-shadow: 2px 2px 5px 2px #c9c9c9;
  height: 2.5rem;
  width: 100%;
  border: 1px;
  border-radius: 20px;
  padding: 0px 20px;
  padding-right: 40px; /* 이미지를 우측에 표시하기 위한 오른쪽 패딩 추가 */
}

.searchmark {
  position: absolute;
  height: 2rem;
  width: 2rem;
  cursor: pointer;
  position: absolute;
  top: 50%;
  right: 8px; /* 이미지를 우측으로 이동 */
  transform: translateY(-50%);
  width: 2.5rem; /* 이미지의 크기 조정 (원하는 크기로 설정) */
  height: 2.5rem;
  cursor: pointer; /* 마우스 커서 모양을 포인터로 변경하여 클릭 가능한 것처럼 보이게 함 */
}
</style>
