<template>
  <div>
    <div class="logincontainer">
      <transition name="fade1">
        <img
          v-if="store.triggerTwo"
          :src="loginbackImg"
          alt="oh,no!"
          class="bgimg"
        />
      </transition>
      <transition name="fade2">
        <img
          v-if="!store.triggerTwo"
          :src="findbackImg"
          alt="oh,no!"
          class="bgimg"
        />
      </transition>

      <div :class="store.triggerTwo ? 'formone' : 'formone flipaddon-front'">
        <div :class="store.triggerOne ? 'temp' : 'formmove'">
          <transition name="fade1" class="moveanimation">
            <div v-if="store.triggerOne">
              <p>로그인폼</p>
              <v-btn @click="moveToMain">login</v-btn>
              <v-btn @click="goToSignup">계정찾기</v-btn>
            </div>
          </transition>
          <transition name="fade2" class="moveanimation">
            <div v-if="!store.triggerOne">
              <p>여기에 회원가입폼</p>
            </div>
          </transition>
        </div>

        <!-- 스크린 옮기기 -->
        <div :class="store.triggerOne ? 'temp' : 'screenmove'">
          <transition name="fade1" class="moveanimation">
            <img
              v-if="store.triggerOne"
              :src="loginscreenImg"
              alt="Image"
              class="screen"
              @click="triggerShot"
            />
          </transition>
          <transition name="fade2" class="moveanimation">
            <img
              v-if="!store.triggerOne"
              :src="signupscreenImg"
              alt="Image"
              class="screen"
              @click="triggerShot"
            />
          </transition>
        </div>
      </div>
      <div :class="store.triggerTwo ? 'formtwo' : 'formtwo flipaddon-back'">
        <div>
          <img :src="findscreenImg" alt="Image" class="forgotscreen" />
        </div>

        <div>
          <v-btn @click="goToSignup">찾았니??</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useAppStore } from "@/store/app";
import { loginbackImg } from "@/assets/image";
import { loginscreenImg } from "@/assets/image";
import { findbackImg } from "@/assets/image";
import { findscreenImg } from "@/assets/image";
import { signupscreenImg } from "@/assets/image";

const store = useAppStore();

function isLogin() {
  store.login();
  console.log(store.logon);
}

function moveToMain() {
  isLogin();
  router.push({ name: "Home" });
}

function triggerShot() {
  store.triggerActivation();
}

function goToSignup() {
  store.isSignup();
}
</script>

<style scoped>
.logincontainer {
  height: 94.2vh;
  width: 100vw;
  overflow: hidden;
  position: relative;
  transition: transform 1s;
  align-items: center;
  justify-content: center;
  display: flex;
  perspective: 1000px;
  backface-visibility: hidden;
}

.logincontainer img {
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.bgimg {
  position: absolute;
}

.formone,
.formtwo {
  border-radius: 30px;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);
  position: absolute;
  background-color: #ffffff80;
  height: 70vh;
  width: 60vw;
  display: flex;
  top: 21%;
  align-items: center;
  justify-content: center;
  backface-visibility: hidden;
  transform-style: preserve-3d;
  transition: transform 1s ease-out;
}

.formone div,
.formtwo div {
  height: 100%;
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: center; /* 수직 중앙 정렬 */
  justify-content: center; /* 수평 중앙 정렬 */
  backface-visibility: hidden;
  transform: translateZ(15px) perspective(100px);
  transform-style: preserve-3d;
  z-index: 1;
}

.formtwo {
  transform: rotateY(-180deg);
}

.flipaddon-front {
  transform: rotateY(180deg);
}

.flipaddon-back {
  transform: rotateY(0deg);
}

.formmove {
  position: relative;
  transform: translate(100%, 0%) !important;
  transition: all 1s;
  z-index: 2;
}

.screen {
  border-radius: 0px 30px 30px 0px;
  max-height: 100%;
  max-width: 100%;
}

.forgotscreen {
  border-radius: 30px 0px 0px 30px;
  max-height: 100%;
  max-width: 100%;
}

.screenmove {
  position: relative;
  transform: translate(-100%, 0%) !important;
  transition: all 1s;
  z-index: 2;
}

.screenmove img {
  border-radius: 30px 0px 0px 30px;
}

.temp {
  position: relative;
  transition-duration: 1s;
}

.fade1-enter-active,
.fade1-leave-active {
  transition: opacity 1s ease;
}

.fade1-enter-from,
.fade1-leave-to {
  opacity: 0;
}

.fade2-enter-active,
.fade2-leave-active {
  transition: opacity 1s ease;
}

.fade2-enter-from,
.fade2-leave-to {
  opacity: 0;
}

.moveanimation {
  position: absolute;
}
</style>
