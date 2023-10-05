<template>
  <div class="share-container">
    <div class="indexTitle" v-if="shareList.length > 0">내가 쓴 글</div>
    <div class="indexTitle" v-if="shareList.length == 0">
      내가 쓴 글이 없습니다.
    </div>
    <v-container>
      <v-row>
        <v-col
          cols="12"
          xxl="1"
          xl="2"
          lg="3"
          md="4"
          sm="6"
          class="post"
          v-for="post in shareList"
          :key="post.id"
          style="{{margin-top: 1%; }}"
        >
          <PostComponent :post="post" />
        </v-col>
      </v-row>
    </v-container>
    <v-divider :thickness="5" class="border-opacity-25" color="info" inset />
    <div class="indexTitle" v-if="shareLikeList.length > 0">좋아요 누른 글</div>
    <div class="indexTitle" v-if="shareLikeList.length == 0">
      좋아요 누른 글이 없습니다.
    </div>
    <v-container>
      <v-row>
        <v-col
          cols="12"
          xxl="1"
          xl="2"
          lg="3"
          md="4"
          sm="6"
          class="post"
          v-for="post in shareLikeList"
          :key="post.id"
          style="{{margin-top: 1%; }}"
        >
          <PostComponent :post="post" />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup>
import { userStore } from "@/store/userStore";
import { computed, onMounted, ref } from "vue";
import PostComponent from "@/components/ShareViewComponents/PostComponent.vue";

const store = userStore();

const shareList = computed(function () {
  return store.getShareList;
});

const shareLikeList = computed(function () {
  return store.getShareLikeList;
});

onMounted(async () => {
  await store.getMyShare();
  await store.getMyShareLike();
});
</script>

<style>
.share-container {
}

.post {
  min-height: 30%;
}

.indexTitle {
  font-weight: bold;
  text-align: center;
}
</style>
