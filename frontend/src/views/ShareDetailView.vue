<script setup>
  import { profileExampleImg } from '@/assets/image'
  import { shareStore } from '@/store/shareStore';
  import { userStore } from '@/store/userStore';
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import router from '@/router';
  import { like, dislike } from '@/assets/Icon';
  import { likeSharePostAPI, loadLikeHistoryAPI, deleteSharePostAPI, loadShareDetailAPI, saveMyQuoteAPI } from '@/api/shareAPI';
  import Review from '@/components/Common/TotalReviewComponent.vue';

  const route = useRoute();
  const id = route.params.id;
  const post = ref([]);
  const likeCnt = ref(0);
  const dislikeCnt = ref(0);
  const store = userStore();
  const isLike = ref(0);
  const isLogin = ref(false);
  const nickname = ref("");

  const returnToList = () => {
    router.push({ name: "Share"});
  }

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

  const deletSharePost = () => {
    const data = {
      articleId : id,
    }
    deleteSharePostAPI(
    data
    ,
    ({ data }) => {
      let msg="게시글 삭제에 성공했습니다"
      if(data == null){
        msg="게시글 삭제에 실패했습니다"
      }else{
        alert(msg)
        returnToList()
      }
      alert(msg)
    }
    ,
    (error) => {
      console.log(error);
    }
  );
  }
  const saveMyQuote = () => {
    const data = {
      cpuId: post.value.quoteEntity.cpu ? post.value.quoteEntity.cpu.id : null ,
      mainboardId: post.value.quoteEntity.mainboard ? post.value.quoteEntity.mainboard.id : null,
      ssdId: post.value.quoteEntity.ssd ? post.value.quoteEntity.ssd.id : null,
      ramId: post.value.quoteEntity.ram ? post.value.quoteEntity.ram.id : null,
      gpuId: post.value.quoteEntity.gpu ? post.value.quoteEntity.gpu.id : null,
      chassisId: post.value.quoteEntity.chassis ? post.value.quoteEntity.chassis.id : null,
      powerId: post.value.quoteEntity.power ? post.value.quoteEntity.power.id : null,
      coolerId: post.value.quoteEntity.cooler ? post.value.quoteEntity.cooler.id : null,
    }
    saveMyQuoteAPI(
    data
    ,
    ({ data }) => {
      console.log(data)
    }
    ,
    (error) => {
      console.log(error);
    }
  );


  }

  const loadShareDetail = () => {
    const data = {
      articleId : id,
    }
    loadShareDetailAPI(
    data
    ,
    ({ data }) => {
      post.value= data
      likeCnt.value += data.likeCnt
      dislikeCnt.value += data.dislikeCnt
    }
    ,
    (error) => {
      console.log(error);
    }
  );
  }


  onMounted(()=>{
    isLogin.value = store.isLogin;
    if(isLogin.value){
      nickname.value = store.getUserInfo.nickname;
    }
    const data = { articleId : id,}

    // 해당 id의 post를 가져옴
    loadShareDetail()

    // 만약 해당 유저가 로그인 상태면 해당 글의 유저의 좋아요 기록을 가져옴
    if(isLogin.value){
      loadLikeHistoryAPI(
      data
      ,
      ({data}) => {
        isLike.value = data.mark
        if(data.mark==1){
          likeCnt.value -= 1
        }
        if(data.mark == -1){
          dislikeCnt.value -= 1
        }
      }
      ,
      (error) => {
        console.log(error);
      }
      );
    }

  })

</script>

<template>
  <v-container class="container">
    <v-row class="header">
      <div class="main-title">
        <h1>
          제목 : {{ post?.title }}
        </h1>
      </div>
      <div class="btn-box">
        <v-btn
        class="btn"
        color="rgba(112, 110, 110, 0.7)"
        @click="returnToList"
        >목록으로 돌아가기</v-btn>
        <v-btn
        v-if="isLogin && nickname == post.userNickname"
        class="btn"
        color="rgba(112, 110, 110, 0.7)"
        @click="deletSharePost"
        >
        글 삭제하기</v-btn>
        <v-btn
        v-if="isLogin"
        class="btn"
        color="rgba(112, 110, 110, 0.7)"
        @click="saveMyQuote"
        >
        마이견적에 저장하기</v-btn>
      </div>
    </v-row>
    <v-row class="body">
      <div class="ml-5 my-5">
        <h2>
          <div v-for="(component, componentName) in post?.quoteEntity" :key="componentName">
            <div>{{ component && component.name ? componentName : "" }} {{ component && component.name ? ": " + component.name : ""}}</div>
          </div>
        </h2>
      </div>
      <div class="ml-5 my-5">
        <h2>
          {{ post?.content }}
        </h2>
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
          작성자 : {{ post?.userNickname }}
        </h3>
      </v-col>
      <!-- <v-col
        class="profileImageBox"
        cols="2">
        <img
        :src="profileExampleImg"
        class ="profileImage"
        alt="profileExample">
      </v-col> -->
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
      <div class="review-title">
        <span>
          <h1>
            리뷰
          </h1>
        </span>
      </div>
    </div>
    <Review
      class="input"
      :articleId = "id"/>
  </v-container >

</template>

<style scoped>


.btn-box {
  display: flex;
}
.btn {
  margin-left: 2%;
}
.container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;

}

.input {
  width: 80%;
}

.footer {
  width: 100%;

}

.header, .review-title {
    width: 100%;
    margin: 2% 0%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 1rem;
    border-bottom: 0.1rem solid black;
}


.body {
  display: flex;
  width: 80%;
  height: 600px;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: flex-start;
  border: 1px solid rgba(187, 181, 181, 0.667);
  border-radius: 2rem;
  margin-bottom: 2%;
}

.content {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: flex-start;

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
