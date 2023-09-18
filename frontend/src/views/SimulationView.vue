<template>
  <div class="container">
    <div class="banner">
      <img :src="simulationbannerImg" alt="" />
      <div class="banner-msg">
        <p>시뮬레이션에서는 부품간의 호환성을</p>
        <p>확인할 수 있습니다.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { simulationbannerImg } from "@/assets/image";
</script>

<style scoped>
.container {
  height: 100%;
  width: 100%;
}

.banner {
  position: relative; /* 부모 요소에 상대 위치 설정 */
  height: 20rem;
  width: 100vw;
}

.banner img {
  height: 20rem;
  width: 100%;
}

.banner-msg {
  position: absolute; /* <p> 요소의 절대 위치 설정 */
  bottom: 30%; /* 이미지 아래에 위치하도록 설정 */
  left: 0; /* 이미지 왼쪽에 위치하도록 설정 */
  padding: 10px; /* 필요한 경우 텍스트 주변에 여백을 추가할 수 있습니다. */
  width: 100%; /* 부모 요소 너비와 동일하게 설정 */
  text-align: center; /* 텍스트를 중앙 정렬 */
  color: #fff;
  font-size: 2rem;
  font-weight: bold;
}

.banner p {
  margin: 0; /* 기본 마진 제거 */
  text-shadow: 2px 2px 5px #00000080;
}
</style>
