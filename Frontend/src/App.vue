<template>
  <div>
    <div class="navbar">
      <div class="navbar-item">
        <RouterLink to="/"> PC.GG </RouterLink>
      </div>
      <!-- 조건부 랜더링. 리액트에 비해 간단 -->
      <!-- 피니아에서 logon변수를 가져옴. logon이 true(즉, 로그인 한상태에서)나타나는 것들 -->
      <!-- a태그와 달리 라우터링크 이용하면 컴포넌트 넘어갈때 새로고침 안함. 목적에 따라 병용가능 -->
      <div v-if="store.logon" class="navbar-right">
        <div class="navbar-item">
          <RouterLink to="/login" @click="isLogout">logout</RouterLink>
        </div>
        <RouterLink to="/" class="navbar-item"
          ><img :src="mypage" alt="mypage"
        /></RouterLink>
        <RouterLink to="/" class="navbar-item"
          ><img :src="bell" alt="bell"
        /></RouterLink>
      </div>
      <!-- logon이 false일때 -->
      <div v-else class="navbar-right">
        <div class="navbar-item">
          <RouterLink to="/login">login</RouterLink>
        </div>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script setup>
import bell from "@/assets/bell.png";
import mypage from "@/assets/mypage.png";
// 스토어 파일(.js파일임)에서 임포트해오기
import { useAppStore } from "@/store/app";
// 분명히 사용하고 있음에도 사용안하는거처럼 나옴. 왠지 모름. 에러 안남
import { RouterLink, RouterView } from "vue-router";

// 이런식으로 정의해서 일반 변수처럼 사용할 수있다고 함
const store = useAppStore();
console.log(store.logon);

function isLogout() {
  store.logout();
  console.log(store.logon);
}
</script>

<!-- 이하는 이 컴포넌트에서만 작동하는 스타일 -->
<style scoped>
body {
  position: relative;
  overflow: auto;
}

a {
  text-decoration: none;
  color: #ffffff;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  background: #000000;
  display: flex;
  justify-content: space-between;
  width: 100%;
  z-index: 100;
}

.navbar-item {
  padding: 0px 5px;
  color: #ffffff;
}

.navbar-item img {
  height: 1em;
}

.navbar-right {
  display: flex;
}
</style>
