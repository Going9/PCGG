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
        <RouterLink to="/">
          <img
            alt="hambergerIcon"
            class="hamberger"
            src="@/assets/Icon/hambergerIcon.png"
          />
        </RouterLink>
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
        <RouterLink to="/">
          <img
            alt="profileIcon"
            class="profile"
            src="@/assets/Icon/profileIcon.png"
            @click="testEvent"
          />
        </RouterLink>
        <RouterLink to="/">
          <img alt="bellIcon" class="bell" src="@/assets/Icon/bellIcon.png" />
        </RouterLink>
        <RouterLink to="/">
          <img
            alt="hambergerIcon"
            class="hamberger"
            src="@/assets/Icon/hambergerIcon.png"
          />
        </RouterLink>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { RouterLink } from "vue-router";
import { test } from "@/api/userAPI";
import { userStore } from "@/store/userStore";
import router from "@/router";

const store = userStore();

const testEvent = () => {
  test(
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};

const logoutEvent = () => {
  store.logout();
  router.push({ name: "Home" });
};
</script>

<style scoped>
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
</style>
