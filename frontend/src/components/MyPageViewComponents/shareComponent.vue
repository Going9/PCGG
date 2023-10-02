<template>
  <div class="share-container">
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
</style>
