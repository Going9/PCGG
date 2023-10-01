<template>
  <div class="background">
    <div class="userinfo">
      <v-img class="banner" cover src="@/assets/anohter.png" />
      <v-img class="profilePicture" cover src="@/assets/anohter.png" />
      <div class="nickname">
        {{ nickname }}
      </div>
    </div>
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
    <div class="productinfo">
      <profile-component v-if="category === 'share'" />
      <saved-quote-component v-if="category === 'savedQuote'" />
      <peripheral-component v-if="category === 'peripheral'" />
      <used-market-component v-if="category === 'usedMarket'" />
    </div>
  </div>
</template>

<script setup>
import { userStore } from "@/store/userStore";
import { onMounted, ref } from "vue";
import ProfileComponent from "../components/MyPageViewComponents/shareComponent.vue";
import SavedQuoteComponent from "../components/MyPageViewComponents/savedQuoteComponent.vue";
import PeripheralComponent from "../components/MyPageViewComponents/peripheralComponent.vue";
import UsedMarketComponent from "../components/MyPageViewComponents/usedMarketComponent.vue";

const store = userStore();
const userInfo = store.getUserInfo;
const nickname = ref(userInfo.nickname);
const category = ref(null);
const buttonItems = [
  { label: "공유마당", value: "share" },
  { label: "저장견적", value: "savedQuote" },
  { label: "주변기기", value: "peripheral" },
  { label: "중고장터", value: "usedMarket" },
];

onMounted(() => {
  category.value = store.getCategory;
});

const buttonEvent = async (value) => {
  category.value = value;
  await store.setCategory(value);
};
</script>

<style scoped>
.background {
  background-color: rgba(
    243.00000071525574,
    242.00000077486038,
    239.00000095367432,
    1
  );
  width: 100%;
  height: 2000px;
  position: absolute;
}
.userinfo {
  background-color: rgba(255, 255, 255, 1);
  width: 1200px;
  height: 450px;
  position: relative;
  margin: auto;
  margin-top: 50px;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
}
.banner {
  background-color: rgba(
    217.0000022649765,
    217.0000022649765,
    217.0000022649765,
    1
  );
  width: 100%;
  height: 40%;
  position: absolute;
  /* background-image: url(@/assets/anohter.png); */
  /* background-repeat: no-repeat; */
  /* background-size: cover; */
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}
.profilePicture {
  border-radius: 50%;
  background-color: rgba(
    217.0000022649765,
    217.0000022649765,
    217.0000022649765,
    1
  );
  width: 200px;
  height: 200px;
  /* width: 18.4%;
  height: 48.5%; */
  position: absolute;
  margin-top: 5%;
  margin-left: 10%;
  /* background-image: url(@/assets/anohter.png); */
  /* background-repeat: no-repeat; */
  /* background-size: cover; */
  border: 3px solid rgba(255, 255, 255, 1);
}
.nickname {
  font-size: 40px;
  position: absolute;
  top: 45%;
  left: 30%;
}
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
.productinfo {
  background-color: rgba(255, 255, 255, 1);
  width: 1200px;
  height: 1200px;
  position: relative;
  margin: auto;
  margin-top: 50px;
}
</style>
