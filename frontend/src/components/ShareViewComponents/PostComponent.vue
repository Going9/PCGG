<script setup>
   import { profileExampleImg } from '@/assets/image'
   import { defineProps } from 'vue'
   import router from '@/router';

    const { post } = defineProps(['post']);

    const goDetail = () => {
      router.push({ name: "ShareDetail", params: {id: post.id} });
    };

</script>

<template>
  <div
  class="container">
    <div class="card-up">
      <div
      @click ="goDetail"
      class="content">
        <span>
          {{post.summary}}
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
              제목 : {{post.title}}
            </v-col>
            <v-col class="custom-padding">
              추천인 : {{post.nickname}}
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
