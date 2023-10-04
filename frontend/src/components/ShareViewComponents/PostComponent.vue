<script setup>
   import { profileExampleImg } from '@/assets/image'
   import { defineProps } from 'vue'
   import router from '@/router';
   import { review, like, dislike } from '@/assets/Icon'

    const { post } = defineProps(['post']);

    const goDetail = () => {
      router.push({ name: "ShareDetail", params: {id: post.id} });
    };

    const formatDate = (dateString) => {
            const dateObj = new Date(dateString);
            const formattedDate = `${String(dateObj.getMonth() + 1)}.${String(dateObj.getDate())} ${String(dateObj.getHours()).padStart(2, '0')}:${String(dateObj.getMinutes()).padStart(2, '0')}`;
            return formattedDate;
        }

</script>

<template>
  <div
  class="container"
  @click ="goDetail">
  <div class="header">
    <div
    class="date">
      작성일 : {{ formatDate(post.createdAt) }}
    </div>
    <div class="title">
      <b>
        견적명 : {{post.title}}
      </b>
    </div>
  </div>
    <div class="body">
      <div
      class="content">
        <span>
          {{post.summary}}
        </span>
      </div>
      <div
        class="side-effect">
        <img :src="like" alt="like" class="img mr-1">
        <span class="font-lg mr-3">
          {{ post.likeCnt }}
        </span>
        <img :src="dislike" alt="dislike" class="img mr-1">
        <span class="font-lg mr-3">
          {{ post.dislikeCnt }}
        </span>
        <img :src="review" alt="review" class="img mr-1">
        <span class="font-lg mr-3">
          {{ post.reviewCnt }}
        </span>
      </div>
    </div>

    <div
    class="footer">
      <v-container
       style="{{ padding: 0rem; }}">
        <v-row class="custom-padding">
          <v-col
           cols="5">
          </v-col>
          <v-col
            class="nickname">
            작성자 : <b>{{post.userNickname}}</b>
          </v-col>
          <!-- <v-col
          cols="2"
          class="profileImage">
            <img :src="profileExampleImg" alt="profileExample">
          </v-col> -->
        </v-row>
      </v-container>

    </div>
  </div>
</template>

<style scoped>
.footer img {
    width: 2.5rem;
    height: 2.5rem;
    border-radius:2.5rem;
}

.nickname {
  margin: 0 3%;
  padding: 0;
  font-size: small;
  text-align: right;
}
 .body {
    margin: 1% 1% 0;
    position: relative;
    height: 50%;

    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
 }

 .footer {
  display: flex;
  align-items: first baseline;
  height: 10%;
  border-radius: 0 0 0.3125rem 0.3125rem;
 }

 .content {
  margin : 1rem;
  font-size: small;
 }

 .side-effect {
  display: flex;
  align-items: center;
  justify-content: center;
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
  /* padding: 0.1rem 0rem 0rem 0.1rem; */
  padding: 0.2rem;
 }
 v-container {
  padding: 0rem;
 }

 .date{
  margin: 2%;
  font-size: small;
  text-align: end;
 }

 .title {
  margin: 0 3%;
  font-size: smaller;
  text-align: start;
 }

 .font-lg {
  padding: 0 1%;
  font-size: large;
 }

 .img {
  width: 60%;
  height: 60%;
 }
</style>
