<template>
  <div class="container">
    <nav>
      <RouterLink to="/" class="logo">
        <span class="text">PC.GG</span>
      </RouterLink>
      <div class="nav-tap">
        <RouterLink to="/login" class="login">
          <span class="text"> Login</span>
        </RouterLink>
        <RouterLink to="/">
          <img
            alt="profileIcon"
            class="profile"
            src="@/assets/icon/profileIcon.png"
          />
        </RouterLink>
        <RouterLink to="/">
          <img alt="bellIcon" class="bell" src="@/assets/icon/bellIcon.png" />
        </RouterLink>
        <RouterLink to="/">
          <img
            alt="hambergerIcon"
            class="hamberger"
            src="@/assets/icon/hambergerIcon.png"
          />
        </RouterLink>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
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
  gap: 1.9375rem;
}

.login {
  display: flex;
  text-align: center;
  width: 3.75rem;
  height: 1.1875rem;
  flex-direction: column;
  justify-content: center;
}

.login .text {
  color: var(--Color, #fff);
  text-align: right;
  font-family: Plus Jakarta Sans;
  font-size: 1.0625rem;
  font-style: normal;
  font-weight: 500;
  line-height: 150%; /* 1.59375rem */
  letter-spacing: -0.03188rem;
}
</style>
