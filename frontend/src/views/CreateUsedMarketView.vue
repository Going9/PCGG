<template>
  <div class="container">

    <div class="header">
      <span class="main-title">
        <h1>
          중고거래 글쓰기
        </h1>
      </span>
      <span class="button">
        <v-btn
          color="blue"
          text="완료"
          @click="createPostEvent"
        ></v-btn>
      </span>
    </div>
    <div class="content">

      <div class="sub-title">
        <h2>
          제목
        </h2>
      </div>
      <div class="title-input input">
        <v-text-field v-model="title2"
          label="제목"
          :rules="rules"
          hide-details="auto" 
        ></v-text-field>
      </div>
      <div class="sub-title">
        <h2>
          내용
        </h2>
      </div>

      <div class="title-input input">
        <v-text-field v-model="content"
          label="내용"
          :rules="rules"
          hide-details="auto" 
        ></v-text-field>
      </div>
      <div class="sub-title">
        <h2>
          가격
        </h2>
      </div>
      <div class="title-input input">
        <v-text-field v-model="price"
          label="가격"
          :rules="rules"
          hide-details="auto" 
        ></v-text-field>
      </div>
      <div class="sub-title">
        <h2>
          한 줄 요약
        </h2>
      </div>
      <div class="info-input input">
        <v-text-field v-model="summary"
          label="요약"
          :rules="rules"
          hide-details="auto"
        ></v-text-field>
      </div>
    </div>

  </div>
</template>

<script setup>
import { usedMarketStore } from "@/store/usedmarketStore";
import { usedMarketAPI } from "@/api/usedmarketAPI";
import {ref} from 'vue';
import router from "@/router";

const store = usedMarketStore();

const title2 = ref("");
const summary = ref("");
const content = ref("");
const price = ref("");

const createPostEvent = () => {
  const UsedMarketInput = {
    title: title2.value,
    summary: summary.value,
    content: content.value,
    price: price.value,
  };

  usedMarketAPI(
    UsedMarketInput,
    ({ data }) => {
      let msg = "게시글 작성이 완료되었습니다.";
      if (data == null) {
        msg = "게시글 작성이 실패되었습니다.";
      }
      alert(msg);
      router.push({name: "UsedMarket"});
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;

}

.header {
    width: 100%;
    margin: 2% 10%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 1rem;
    border-bottom: 0.1rem solid black;
}


.content {
  display: flex;
    width: 100%;
    flex-direction: column;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
}

.sub-title {
  width: 64%;
  text-align:left;
  margin: 2%;
  padding-bottom: 1%;
  border-bottom: 1px solid black;
}
.input {
  margin: 0 5% 5%;
  width: 64%;
}

.select {
  width: 64%;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  /* border: 1px solid rgba(187, 181, 181, 0.667); */
  border-radius: 2rem;
}

.select .input {
  margin: 5% 6%;
  width: 80%;

}

</style>
