<template>
  <div class="container">
    <div class="searchbar">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="검색"
            id="searchbar-inner"
            @keyup.enter="goSearch"
            class="searchbar-inner"
          />
          <img
            :src="searchIcon"
            alt="돋보기"
            @click="goSearch"
            class="searchmark"
          />
      </div>
  </div>
</template>

<script setup>
  import {ref , defineEmits} from 'vue';
  import { searchIcon } from "@/assets/Icon";
  const emit = defineEmits(['q']);
  const searchQuery = ref("");

  const goSearch = () => {
    emit('q',searchQuery.value);
    // 검색 후 입력 내용 초기화
    searchQuery.value = "";
  };
</script>

<style scoped>

  .container {
    margin-top : 3%;
    width : 100%;
    text-align: center;
  }

  .searchbar {
  margin: auto;
  margin-top: 0px;
  margin-bottom: 0px;
  width: 50%;
  position: relative;
  display: inline-block;
}

.searchbar-inner {
  box-shadow: 2px 2px 5px 2px #c9c9c9;
  height: 2.5rem;
  width: 100%;
  border: 1px;
  border-radius: 20px;
  padding: 0px 20px;
  padding-right: 40px; /* 이미지를 우측에 표시하기 위한 오른쪽 패딩 추가 */
}

.searchmark {
  position: absolute;
  height: 2rem;
  width: 2rem;
  cursor: pointer;
  position: absolute;
  top: 50%;
  right: 8px; /* 이미지를 우측으로 이동 */
  transform: translateY(-50%);
  width: 2.5rem; /* 이미지의 크기 조정 (원하는 크기로 설정) */
  height: 2.5rem;
  cursor: pointer; /* 마우스 커서 모양을 포인터로 변경하여 클릭 가능한 것처럼 보이게 함 */
}

</style>
