<script setup>
  import { profileExampleImg } from '@/assets/image'
  import { shareStore } from '@/store/shareStore';
  import { userStore } from '@/store/userStore';
  import { defineProps, ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import { like, dislike } from '@/assets/Icon';
  import { likeSharePostAPI, loadLikeHistoryAPI } from '@/api/shareAPI';

  const route = useRoute();
  const id = route.params.id;
  const post = ref([]);
  const store = shareStore();
  const likeCnt = ref(0);
  const dislikeCnt = ref(0);
  const isLike = ref(0);
  const isLogin = ref(false);


  const selectlike = (value) => {
    console.log(isLogin.value)
    if(!isLogin.value){
      const msg = "로그인이 필요합니다"
      return alert(msg)
    }
    const data = {
      articleId : id,
      mark : value
    }
    console.log(data)
    likeSharePostAPI(
    data
    ,
    ({ data }) => {
      if(isLike.value == data){
        isLike.value = 0
      }else{
        isLike.value = data
      }
    }
    ,
    (error) => {
      console.log(error);
    }
  );
};


  onMounted(()=>{
    isLogin.value = userStore().isLogin;
    const shareList = store.isShareList;
    const data = { articleId : id,}

    // 해당 글의 유저의 좋아요 기록을 가져옴
    loadLikeHistoryAPI(
    data
    ,
    ({data}) => {
      console.log(data)
      isLike.value = data
    }
    ,
    (error) => {
      console.log(error);
    }
    );
    // 리스트에서 해당 id의 post를 가져옴
    setTimeout(()=>{
      post.value= shareList.filter((post)=> post.id == id )[0]
    },100)

  })

</script>

<template>
  <v-container class="container">
    <v-row class="header">
      <span class="main-title">
        <h1>
          제목 : {{ post?.title }}
        </h1>
      </span>
    </v-row>
    <v-row class="content">
      <div>
        <h1>
          컴퓨터 견적 내용 {{ post?.quoteld }}
        </h1>
      </div>
    </v-row>

    <v-row class="summery-info">
      <v-col
        cols="8">
        <h3>
          한 줄 설명 : {{ post?.summary }}
        </h3>
      </v-col>
      <v-col
        cols="2">
        <h3>
          추천인 : {{ post?.userId }}
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

    <v-row>
      <v-col class="like">
        <v-col
        class="like-label"
        cols="5">
        <th>
          추천
        </th>
        </v-col>
        <v-col
          @click="selectlike(1)"
          cols="2">
          <img :src="like" alt="like">
        </v-col>
        <v-col
        :style="{ color: isLike === 1 ? 'blue': undefined }"
        class="like-cnt"
        cols="5">
          {{ isLike === 1 ? likeCnt+1 : likeCnt }}
        </v-col>
      </v-col>
      <v-col class="dislike">
        <v-col
          class="dislike-label"
          cols="5">
          <th>
            비추천
          </th>
        </v-col>
        <v-col
          @click="selectlike(-1)"
          cols="2">
          <img :src="dislike" alt="dislike">
        </v-col>
        <v-col
        :style="{ color: isLike === -1 ? 'red' : undefined }"
        class="dislike-cnt"
        cols="5">
        {{ isLike == -1 ? dislikeCnt+1 : dislikeCnt }}
        </v-col>
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
  width: 100%;
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

.like, .dislike {
  display: flex;
  text-align: right;
}

.like-label, .dislike-label {
  display: flex;
  justify-content: flex-end;
}

.like-cnt, .dislike-cnt {
  display: flex;
  justify-content: flex-start;
}

</style>
