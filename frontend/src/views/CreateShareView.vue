<script setup>
import PartSelectionComponent from '@/components/Common/PartSelectionComponent.vue';
import { createSharedPost } from "@/api/shares";

const createSharedPostEvent = () => {
    const data = {
      userId: 3,
      title: "string",
      content: "string",
      imageSource: "string",
      summary: "string",
      shareAddQuoteRequestDto: {
      cpuId: 1,
      mainboardId: 1,
      ssdId: 1,
      ramId: 1,
      gpuId: 1,
      chassisId: 1,
      powerId: 1,
      coolerId: 1,
  } }
      createSharedPost(
      data
      ,
      ({ data }) => {
        let msg = "공유 게시글 작성에 성공했습니다.";
        if (data == null) {
          msg = "공유 게시글 작성에 실패했습니다.";
        }
        alert(msg);
      },
      (error) => {
        console.log(error);
      }
    );

  }
</script>

<template>
  <div class="container">

    <div class="header">
      <span class="main-title">
        <h1>
          견적공유 글쓰기
        </h1>
      </span>
      <span class="button">
        <v-btn
          color="blue"
          text="완료"
          @click="createSharedPostEvent"
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
        <v-text-field
          label="제목"
          hide-details="auto"
        ></v-text-field>
      </div>
      <div class="sub-title">
        <h2>
          부품 선택
        </h2>
      </div>
      <div class="select">
        <PartSelectionComponent/>
      </div>

      <div class="sub-title">
        <h2>
          한 줄 설명
        </h2>
      </div>
      <div class="info-input input">
        <v-text-field
          label="한 줄 설명"
          hide-details="auto"
        ></v-text-field>
      </div>
    </div>

  </div>
</template>

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
  width: 75%;
  text-align:left;
  margin: 2%;
  padding-bottom: 1%;
  border-bottom: 1px solid black;
}
.input {
  margin: 0 5% 5%;
  margin-left: 0;
  width: 60%;
}

.select {
  width: 75%;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border-radius: 2rem;
}

.select .input {
  margin: 5% 6%;
  width: 80%;

}

</style>
