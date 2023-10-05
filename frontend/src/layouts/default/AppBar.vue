<template>
  <div class="container">
    <!-- 로그인 Before -->
    <nav v-if="!store.isLogin">
      <RouterLink to="/" class="logo">
        <span class="text">PC.GG</span>
      </RouterLink>
      <div class="nav-tap">
        <RouterLink to="/login" class="login">
          <span v-if="!store.isLogin" class="text"> Login</span>
        </RouterLink>
        <div
         @click="toggleIsCategory">
          <img
            alt="hambergerIcon"
            class="hamberger"
            src="@/assets/Icon/hambergerIcon.png"
          />
        </div>
      </div>
    </nav>

    <!-- 로그인 After -->
    <nav v-if="store.isLogin">
      <RouterLink to="/" class="logo">
        <span class="text">PC.GG</span>
      </RouterLink>
      <div class="nav-tap">
        <RouterLink to="/" class="logout">
          <span v-if="store.isLogin" class="text" @click="logoutEvent">
            Logout</span
          >
        </RouterLink>
        <RouterLink to="/mypage">
          <img
            alt="profileIcon"
            class="profile"
            src="@/assets/Icon/profileIcon.png"
          />
        </RouterLink>
        <div
         @click="toggleIsCategory">
          <img
            alt="hambergerIcon"
            class="hamberger"
            src="@/assets/Icon/hambergerIcon.png"
          />
        </div>
      </div>
    </nav>

    <div class="category" v-if="isCategory">
      <v-container>
        <v-col cols="12">
          <h1>분류</h1>
        </v-col>
        <hr>
        <v-col cols="12">
          <RouterLink to="/share" class="no-deco">
            <h2>
              공유 마당
            </h2>
          </RouterLink>
        </v-col>
        <hr>
        <v-col cols="12">
          <RouterLink to="/peripheral" class="no-deco">
            <h2>
              주변기기
            </h2>
          </RouterLink>
        </v-col>
        <hr>
        <v-col cols="12">
          <RouterLink to="/recommendation" class="no-deco">
            <h2>
              견적랭킹
            </h2>
          </RouterLink>
        </v-col>
        <hr>
        <v-col cols="12">
          <RouterLink to="/simulation" class="no-deco">
            <h2>
              시뮬레이션
            </h2>
          </RouterLink>
        </v-col>
        <hr>
      </v-container>

    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import { RouterLink } from "vue-router";
import { userStore } from "@/store/userStore";
import router from "@/router";


const isCategory = ref(false)
const store = userStore();

const toggleIsCategory = ()=>{
  console.log(isCategory.value)
  isCategory.value = !isCategory.value
}

const logoutEvent = () => {
  store.logout();
  router.push({ name: "Home" });
};
</script>

<style scoped>
.container {
  position: relative;
}

.category {
 z-index: 100;
 position: absolute;
 right: 0;
 height: 100vh;
 min-width: 20%;
 background-color: white;
}
.text {
  color: white;
}

nav {
  width: 100%;
  height: 2.7rem;
  flex-shrink: 0;
  font-size: 12px;
  text-align: center;
  margin-top: 0rem;

  border-radius: var(--Number, 0rem);
  background: #000;

  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  display: flex;
  width: 4.875rem;
  height: 1.8125rem;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  text-decoration: none;
}

.logo .text {
  color: var(--Color, #fff);
  text-align: center;
  font-family: Inter;
  font-size: 1.5rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

nav .nav-tap {
  display: inline-flex;
  padding: var(--Number, 0rem);
  justify-content: center;
  align-items: center;
}

.login,
.logout {
  display: flex;
  text-align: center;
  height: 1.1875rem;
  flex-direction: column;
  justify-content: center;
  text-decoration: none;
}

.login .text,
.logout .text {
  color: var(--Color, #fff);
  text-align: right;
  font-family: Plus Jakarta Sans;
  font-size: 1.0625rem;
  font-style: normal;
  font-weight: 500;
  line-height: 150%; /* 1.59375rem */
  letter-spacing: -0.03188rem;
}

.sloth {
  display: flex;
}

.no-deco{
  text-decoration: none;
  color: black;
}
</style>
