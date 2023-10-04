<template>
    <div class="container">
      <div class="card-up">
        <v-btn class= "close-button" icon>
          <img :src="close" alt="">
        </v-btn>
      </div>
      <div class="card-down">
        <p>{{ post.chssis }}</p>
      </div>
    </div>
  </template>
  
  
  <script setup>
     import { close } from '@/assets/Icon';
     import { defineProps } from 'vue';
     
     const { post } = defineProps(['post']);
  </script>
  <style scoped>
  .container {
    height: 100%;
    width: 100%;
  
    border-radius: 10px;
    background-color: #F4F4F4;
    box-shadow: 0px 16px 40px rgba(112, 128, 144, 0.20); 
  }
  
  .header {
    height:3%;
    position: relative;
  
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    flex-direction: column;
  }
  .card-up {
    margin: 1% 1% 0;
    position:relative;
    height: 60%;
    background-color:r #F4F4F4;
    display: flex;
    justify-content: right;
    flex-wrap: wrap;
    flex-direction: column;
  }
  
  .card-down {
    text-align: center;
  }
  
  .close-button {
    margin-top: 15px;
    margin-left: 95%;
    display: flex;
    justify-content: center;
    align-items: right;
    background-color: #FF605C;
    color: white;
  }
  .card-up close-button {
    width: 0.5rem;
    height: 0.5rem;
    border-radius: 0.5rem;
    text-align:right;
  }
  
  .card-up img {
    width: 1rem;
    height: 1rem;
  }
  
  v-container {
    padding: 0rem;
  }
  
  </style>
  