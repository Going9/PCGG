<template>
  <div class="container">
    <div v-if="store.searchToggle">
      <p>검색결과</p>
      <PeripheralSearchComponentVue />
    </div>
    <v-divider
      class="border-opacity-100"
      style="margin-top: 2rem"
      v-if="store.searchToggle"
    ></v-divider>
    <!-- 분리선 -->
    <div class="text" v-if="user.loginActivated">
      <span class="username">{{ user.userInfo["nickname"] }}</span
      ><span>님께 추천드리는 </span>
      <span v-if="store.peripheralCategory === 'keyboard'">키보드</span>
      <span v-if="store.peripheralCategory === 'mouse'">마우스</span>
      <span v-if="store.peripheralCategory === 'monitor'">모니터</span>
      <span v-if="store.peripheralCategory === 'printer'">프린터와 복합기</span>
      <span v-if="store.peripheralCategory === 'etc'">주변기기</span>
    </div>
    <PeripheralRecommedComponentVue
      class="recommend"
      v-if="user.loginActivated"
    />
    <!-- 분리선 -->
    <v-divider
      class="border-opacity-100"
      style="margin-top: 2rem"
      v-if="user.loginActivated"
    ></v-divider>
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
      <PeripheralCardComponentVue />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { usePeripehralStore } from "@/store/peripheralStore";
import { userStore } from "@/store/userStore";
import PeripheralCardComponentVue from "./PeripheralCardComponent.vue";
import PeripheralRecommedComponentVue from "./PeripheralRecommedComponent.vue";
import PeripheralSearchComponentVue from "./PeripheralSearchComponent.vue";

const store = usePeripehralStore();

const user = userStore();

const toggle = ref(null);

const setToggle = (value) => {
  toggle.value = value;
  console.log(toggle);
};

const buttonItems = [
  { label: "낮은가격순", value: "low" },
  { label: "높은가격순", value: "high" },
  { label: "상품명순", value: "name" },
];

onMounted(() => {
  console.log(user.userInfo);
});
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
  justify-content: center;
  align-items: center;
  font-weight: bold;
}
</style>
