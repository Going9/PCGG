<script setup>
   import { profileExampleImg } from '@/assets/image'
</script>

<template>
  <div class="container">
    <div class="card-up">
      <div class="heartIcon">
        <v-icon
          size="large"
          icon="mdi-heart">
        </v-icon>
      </div>
      <span>
        게시글 내용
      </span>
    </div>

    <div class="card-down">
      <v-container
       style="{{ padding: 0rem; }}">
        <v-row class="custom-padding">
          <v-col class="custom-padding">
            <v-col class="custom-padding">
              제목 :
            </v-col>
            <v-col class="custom-padding">
              추천인 :
            </v-col>
          </v-col>
          <v-col class="profileImage">
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
    outline: 1px pink solid;
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
  outline: 1px blue solid;
  height: 40%;
  padding: auto;
  border-radius: 0 0 0.3125rem 0.3125rem;
  /* background-color: black; */
  background-color: #a8a8a870;
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
  padding: 0.2rem 0.2rem 0rem 0.2rem;
 }
 v-container {
  padding: 0rem;
 }
</style>
