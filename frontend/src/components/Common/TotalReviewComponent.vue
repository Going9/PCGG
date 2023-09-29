<script setup>
  import { loadShareReviewAPI, createShareReviewAPI, } from "@/api/shareReviewAPI";
  import { userStore } from "@/store/userStore";
  import {defineProps, ref, onMounted} from 'vue'
  import Review from '@/components/Common/ReviewComponent.vue'
  const { articleId } = defineProps(['articleId']);

  const store = userStore()
  const content = ref("");
  const reviewList = ref([]);
  const isLogin = ref(false)

  onMounted(()=>{
    isLogin.value = store.isLogin;
    loadShareReview();
  })

  const loadShareReview = () => {
    const data = {
      articleId
    }
    loadShareReviewAPI(
      data
      ,
      ({data})=>{
        reviewList.value = data.content
      }
      ,
      (error)=>{
        console.log(error)
      }
    )
  }

  const createShareReview = () => {
    if(!isLogin.value){
      alert("로그인이 필요합니다")
      content.value = ""
      return
    }
    const data = {
      content : content.value,
      articleId
    }
    createShareReviewAPI(
      data
      ,
      ()=>{
        content.value = ""
        loadShareReview()
      }
      ,
      (error)=>{
        console.log(error)
      }
    )
  }

  const isReviewList = () => {
  if (reviewList.value.length === 0) {
    return true;
  }
  return false;
}

</script>

<template>
  <div>
    <v-container>
      <v-row
        class="no-review"
        v-if="isReviewList()">
        첫번째 리뷰의 주인공이 되세요!
      </v-row>
      <v-row
        v-else>
        <v-col
        cols="12"
        class="reviews"
        v-for="review in reviewList"
        :key="review.id">
          <Review
          class="review"
          @change="loadShareReview"
          :review="review"/>
        </v-col>
      </v-row>
      <v-row
      class="input-box">
        <form
          class="input"
          @submit.prevent="createShareReview" >
          <v-text-field
          variant="outlined"
          v-model="content"
          placeholder="리뷰를 작성해주세요"
          ></v-text-field>
        </form>
      </v-row>
    </v-container>
  </div>
</template>

<style scoped>

.no-review {
  height: 10rem;
  display: flex;
  justify-content: center;
  align-items: center;
}

.reviews {
  max-height: 100rem;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.review {
  width: 100%;
  padding: 0;
}

.input-box {
  display: flex;
  justify-content: center;
  align-items: center;
}

.input {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 2%;
  width: 80%;
}
</style>
