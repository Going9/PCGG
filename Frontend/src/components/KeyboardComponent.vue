<template>
  <div class="container">
    <div class="text">
      <span class="username">username</span
      ><span>님께 추천드리는 키보드 ;)</span>
    </div>
    <div class="recommend">
      <div
        v-for="(card, index) in temps"
        :key="index"
        class="cardform"
        @click="goDetail"
      >
        <img :src="card" alt="Card Image" />
        <div class="card-body">
          <h3 class="card-title">매우 비싼 키보드</h3>
          <p class="card-text">가성비 엉망</p>
        </div>
      </div>
    </div>
    <hr />
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
      <div class="list">
        <div
          v-for="(card, index) in temps"
          :key="index"
          class="cardform"
          @click="goDetail"
        >
          <img :src="card" alt="Card Image" />
          <div class="card-body">
            <h3 class="card-title">매우 비싼 키보드</h3>
            <p class="card-text">안팔아</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import temp1 from "@/assets/temp1.png";
import temp2 from "@/assets/temp2.png";
import temp3 from "@/assets/temp3.png";
import temp4 from "@/assets/temp4.png";
import router from "@/router";

const temps = ref([
  temp1,
  temp2,
  temp3,
  temp4,
  temp1,
  temp2,
  temp3,
  temp4,
  temp1,
  temp2,
  temp3,
  temp4,
  temp1,
  temp2,
  temp3,
  temp4,
]);

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

const goDetail = () => {
  console.log("hey?");
  router.push({ name: "PeripheralDetail" });
};
</script>

<style scoped>
.container {
  height: 100%;
  width: 90%;
  padding: 5px;
  background-color: #d9d9d966;
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
  background-color: #fff;
  border-radius: 15px;
}

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
