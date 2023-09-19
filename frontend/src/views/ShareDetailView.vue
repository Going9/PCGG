<script setup>
  import { profileExampleImg } from '@/assets/image'
</script>

<template>
  <v-container class="container">

    <v-row class="header">
      <span class="main-title">
        <h1>
          제목 : 가성비 컴퓨터
        </h1>
      </span>
    </v-row>
    <v-row class="content">
      <div>
        <h1>
          컴퓨터 견적 내용
        </h1>
      </div>
    </v-row>

    <v-row class="summery-info">
      <v-col
        cols="8">
        <h3>
          한 줄 설명 : 가벼운 컴퓨터 작업 기준 가성비 게임 입니다.
        </h3>
      </v-col>
      <v-col
        cols="2">
        <h3>
          추천인 : 유저
        </h3>
      </v-col>
      <v-col
        class="profileImageBox"
        cols="2">
        <img
        :src="profileExampleImg"
        class ="profileImage"
        alt="profileExample">
      </v-col>
    </v-row>

    <div class="footer">
      <span class="main-title">
        <h1>
          리뷰
        </h1>
      </span>
    </div>
  </v-container >

</template>

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;

}

.header, .footer {
    width: 100%;
    margin: 2% 0%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 1rem;
    border-bottom: 0.1rem solid black;
}


.content {
  display: flex;
  width: 80%;
  height: 600px;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border: 1px solid rgba(187, 181, 181, 0.667);
  border-radius: 2rem;
  margin-bottom: 2%;
}

.sub-title {
  width: 64%;
  text-align:left;
  margin: 2%;
  padding-bottom: 1%;
  border-bottom: 1px solid black;
}

.select {
  width: 64%;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border: 1px solid rgba(187, 181, 181, 0.667);
  border-radius: 2rem;
}

.summery-info {
  padding: 1%;
  width: 80%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border: 1px solid rgba(187, 181, 181, 0.667);
  border-radius: 1rem;
  margin-bottom: 2%;

}
.profileImageBox {
    width: 100% ;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
 }
.profileImage {
    width: 20% ;
    height: 20%;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 2rem;
 }



</style>
