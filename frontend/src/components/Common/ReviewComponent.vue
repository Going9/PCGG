<script setup>
  import { modifyShareReviewAPI, deleteShareReviewAPI } from "@/api/shareReviewAPI";
  import {defineProps, defineEmits, ref} from 'vue'
  const { review } = defineProps(['review']);
  const emit = defineEmits(['change']);
  const isModify = ref(false)
  const content = ref("")

  const toggleModifyMode = () => {
    if(!isModify.value){
      content.value = review.content
      isModify.value = !isModify.value
    }else{
      modifyShareReview()
    }
  }

  const deleteShareReview = () => {
    const data = {
      commentId : review.id
    }
    deleteShareReviewAPI(
      data
      ,
      ({data})=>{
        console.log(data)
        // 리뷰 삭제시 리뷰를 갱신하기위해 emit을 사용
        emit('change');
      }
      ,
      (error)=>{
        console.log(error)
      }
    )
  }

  const modifyShareReview = () => {
    const data = {
      content : content.value,
      commentId : review.id
    }
    modifyShareReviewAPI(
      data
      ,
      ({data})=>{
        console.log(data)
        // 리뷰 수정 시 리뷰를 갱신하기위해 emit을 사용
        emit('change');
        isModify.value = !isModify.value
      }
      ,
      (error)=>{
        console.log(error)
      }
    )
  }


</script>

<template>
  <div>
    <v-container class="container">
      <v-col
      v-if="!isModify"
      cols="8"
      class="content">
       내용 : {{ review.content }}
      </v-col>
      <v-col
      v-else
      cols="8"
      class="content">
        <form @submit.prevent="modifyShareReview">
          <v-text-field
          variant="outlined"
          v-model="content"
          ></v-text-field>
        </form>
      </v-col>
      <v-col
      cols="2"
      class="nickname">
        작성자 : {{ review.userNickname }}
      </v-col>
      <v-col
      cols="1">
        <v-btn
        @click="toggleModifyMode"
        >수정</v-btn>
      </v-col>
      <v-col
      cols="1">
        <v-btn
        @click="deleteShareReview"
        >삭제</v-btn>
      </v-col>
    </v-container>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  border-top : solid 1px grey;
  border-bottom : solid 1px grey;
}
</style>
