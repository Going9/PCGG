<template>
  <div class="container">
    <div class="card-down">
      <div class="part-container" v-for="part in partList" :key="part">
        <img
          :src="post[part.value].imageSource"
          class="part-img"
          alt="noimage!"
        />
        <v-divider class="border-opacity-100" vertical></v-divider>
        <div class="part-infos">
          <p class="part-info">{{ part.value }}</p>
          <p class="part-info">가격 : {{ post[part.value].price }}</p>
        </div>
        <div class="part-infos">
          <p class="part-info">{{ part.value }}</p>
          <p class="part-info">가격 : {{ post[part.value].price }}</p>
        </div>
      </div>
    </div>
    <div class="card-up">
      <v-btn class="close-button" icon @click="deleteEvent">
        <img :src="close" alt="" />
      </v-btn>
    </div>
  </div>
</template>

<script setup>
import { close } from "@/assets/Icon";
import { computed, defineProps } from "vue";
import { userStore } from "@/store/userStore";

const { post } = defineProps(["post"]);
const store = userStore();
const partList = [
  { label: "CPU", value: "cpu" },
  { label: "메인보드", value: "mainboard" },
  { label: "SSD", value: "ssd" },
  { label: "RAM", value: "ram" },
  { label: "GPU", value: "gpu" },
  { label: "Chassis", value: "chassis" },
  { label: "Power", value: "power" },
  { label: "쿨러", value: "cooler" },
];

const deleteEvent = async () => {
  await store.deleteMySavedQuote(post.id);
  await store.getMySavedQuote();
};
</script>

<style scoped>
.container {
  width: 100%;
  border-radius: 10px;
  background-color: #f4f4f4;
  box-shadow: 0px 16px 40px rgba(112, 128, 144, 0.2);
  display: flex;
}

.header {
  height: 3%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: column;
}
.card-up {
  position: relative;
  height: 10%;
  background-color: r #f4f4f4;
  margin-top: 10px;
  margin-right: 10px;
}

.card-down {
  text-align: center;
}

.close-button {
  display: flex;
  justify-content: center;
  align-items: right;
  background-color: #ff605c;
  color: white;
}
.card-up close-button {
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 0.5rem;
  text-align: right;
}

.card-up img {
  width: 1rem;
  height: 1rem;
}

v-container {
  padding: 0rem;
}

.part-container {
  display: flex;
  margin: 1rem;
  align-items: center;
  background-color: #d9d9d9;
  border-radius: 10px;
  padding: 1rem;
  transition: border-radius 0.3s ease-in-out;
}

.part-img {
  height: 5rem;
  width: 5rem;
  margin-right: 1rem;
}

.part-infos {
  display: flex;
  flex-direction: column;
  padding: 3px 1rem;
  width: 90%;
  cursor: pointer;
  justify-content: space-between;
}

.part-info {
  display: flex;
  justify-content: space-between;
  margin-right: 2rem;
}
</style>
