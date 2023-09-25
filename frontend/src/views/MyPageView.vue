<template>
  <div class="background">
    <div class="info">
      <v-img class="banner" cover src="@/assets/anohter.png" />
      <v-img class="profile" cover src="@/assets/anohter.png" />
      <div class="nickname">
        {{ nickname }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { userStore } from "@/store/userStore";
import { ref } from "vue";

const store = userStore();

const userInfo = store.getUserInfo;
const nickname = ref(userInfo.nickname);
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
  height: 100%;
  position: fixed;
}
.info {
  background-color: rgba(255, 255, 255, 1);
  width: 71.2%;
  height: 57.6%;
  margin: auto;
  margin-top: 10%;
  position: relative;
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
.profile {
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
  margin: auto;
  margin-top: 5%;
  margin-left: 10%;
  /* background-image: url(@/assets/anohter.png); */
  /* background-repeat: no-repeat; */
  /* background-size: cover; */
  border: 3px solid rgba(255, 255, 255, 1);
}
.nickname {
  width: 100%;
  height: 100%;
  font-size: 100%;
  position: absolute;
  margin-top: 30%;
  margin-left: 40%;
}
</style>
