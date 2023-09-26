<script setup>
  import PostComponent from '@/components/ShareViewComponents/PostComponent.vue'
  import SearchComponent from '@/components/Common/SearchBarComponent.vue'
  import { RouterLink } from 'vue-router';
  import { loadSharedList } from "@/api/shares";
  import { onMounted, ref } from 'vue';

  const postList = ref([])

  const loadSharedListEvent = (value) => {
    const data = { pages : value }
    loadSharedList(
      data
      ,
      ({ data }) => {
        let msg = "공유 게시글 불러오기에 성공했습니다.";
        postList.value = data
        console.log(postList.value.content)
        if (data == null) {
          msg = "공유 게시글이 없습니다.";
        }
        alert(msg);
      },
      (error) => {
        console.log(error);
      }
  );
};

onMounted(() => {
  // loadSharedListEvent(0);
});

</script>

<template>
  <main>
    <div class="banner">
      <img alt="BannerImg" class="bannerImg" src="@/assets/image/share-banner.jpg" />
      <div class="box">
        <div class="bannerComment">
          <span>
            공유마당에서는 사용자들이 추천하는 견적을 참고 할 수 있습니다
          </span>
        </div>
      </div>
    </div>
    <div class="searchbox flex-center">
      <SearchComponent/>
    </div>
      <div class="writebox">
        <RouterLink to="/share/createshare">
          <v-btn color="#4599FC">견적 공유하기</v-btn>
        </RouterLink>
      </div>
      <div class="mainbox flex-center">
        <div class="resultbox">
          <v-container>
            <v-row>
              <v-col
              cols="12"
              xxl="1"
              xl="2"
              lg="3"
              md="4"
              sm="6"
              v-for="postNum in 20"
              :key="postNum"
              style="{{margin-top: 1%; }}" class="post">
              <PostComponent
              style="{{  width: 20%; height: 100%; }}"
              />
            </v-col>
          </v-row>
          </v-container>
      </div>
    </div>
    <router-view />
  </main>
</template>

<style scoped>
.banner {
  position: relative; /* 부모 요소에 상대적 위치를 설정합니다. */
  width: 100%;
  height: 20rem;
}

.bannerImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.box {
  position: absolute; /* 부모 요소에 대해 절대 위치를 설정합니다. */
  top: 50%; /* 상단 중앙에 정렬합니다. */
  left: 50%; /* 좌측 중앙에 정렬합니다. */
  transform: translate(-50%, -50%); /* 중앙 정렬을 위한 변형을 적용합니다. */
  width: 60.5625rem;
  height: 8.6875rem;
  text-align: center;
  color: #fff;
  text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

.bannerComment {
  text-align: center;
  font-family: Inter;
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
}


.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
}
 .writebox {
    margin: 1% 6%;
    display: flex;
    justify-content: flex-end;
 }
 .resultbox {
    margin: 1% 6%;
    /* outline: 1px red solid; */
    /* background-color: bisque; */
    height: 1200px;

    display: flex;
    justify-content: flex-start;
    align-items: center;
    align-content: flex-start;
    flex-wrap: wrap;
    padding: var(--Number, 0rem);
    gap: 3.125rem 3.75rem;
    padding: 1% 0% 0% 1%;
 }

 .post {
  width: 15.75rem;
  height: 12.25rem;
  flex-shrink: 0;
  background: #FFF;

 }
</style>
