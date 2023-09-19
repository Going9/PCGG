<script setup>
  import PostComponent from '@/components/ShareViewComponents/PostComponent.vue'
  import SearchComponent from '@/components/Common/SearchBarComponent.vue'
  import { RouterLink } from 'vue-router';

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
