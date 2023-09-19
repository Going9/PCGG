<script setup>
   import { profileExampleImg } from '@/assets/image'
   import { ref } from 'vue'
   import router from '@/router';

    const goDetail = () => {
      router.push({ name: "ShareDetail" });
    };

   const share_like = ref(-1)

   function Changelike() {
    if (share_like.value == 1){
      share_like.value = -1
    }else{
      share_like.value = 1
    }

   }

</script>

<template>
  <div
  class="container">
    <div class="card-up">
      <div class="heartIcon">
        <v-icon
          @click = "Changelike"
          size="large"
          :color="share_like > 0 ? 'red' : 'grey'"
          icon="mdi-heart">
        </v-icon>
      </div>
      <div
      @click ="goDetail"
      class="content">
        <span>
          가벼운 컴퓨터 작업 기준 가성비 세팅입니다.
        </span>
      </div>
    </div>

    <div
    @click ="goDetail"
    class="card-down">
      <v-container
       style="{{ padding: 0rem; }}">
        <v-row class="custom-padding">
          <v-col
           cols="10"
           class="custom-padding">
            <v-col class="custom-padding">
              제목 : 가성비 세팅
            </v-col>
            <v-col class="custom-padding">
              추천인 : 유저 닉네임
            </v-col>
          </v-col>
          <v-col
          cols="2"
          class="profileImage">
            <img :src="profileExampleImg" alt="profileExample">
          </v-col>
        </v-row>
      </v-container>

    </div>
  </div>
</template>

<style scoped>
  .heartIcon {
    position: absolute;
    top: 0;
    right: 0
  }
  .card-down img {
    width: 2.5rem;
    height: 2.5rem;

    border-radius:2.5rem;
}
 .card-up {
    /* outline: 1px pink solid; */
    margin: 1% 1% 0;
    position: relative;
    height: 60%;

    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    flex-direction: column;
 }

 .card-down {
  /* outline: 1px blue solid; */
  height: 40%;
  padding: auto;
  border-radius: 0 0 0.3125rem 0.3125rem;
  /* background-color: black; */
  background-color: #a8a8a870;
 }

 .content {
  margin : 1rem;
 }
 .container {
  height: 100%;
  width: 100%;

  border-radius: 0.3125rem;
  border: 1px solid #A8A8A8;
  background: rgba(217, 217, 217, 0.10);
 }

 .profileImage {
    display: flex;
    justify-content: center;
    align-items: center;
 }
 .custom-padding {
  padding: 0.1rem 0rem 0rem 0.1rem;
 }
 v-container {
  padding: 0rem;
 }
</style>
