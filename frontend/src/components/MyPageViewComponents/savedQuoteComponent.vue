<template>
  <div class="container">
    <v-container>
      <v-row>
        <v-col
          class="post"
          v-for="post in savedQuoteList"
          :key="post.id"
          style="{{ margin-top: 1%;}}"
        >
          <savedQuoteDetailComponent :post="post" />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup>
import savedQuoteDetailComponent from "@/components/MyPageViewComponents/savedQuoteDetailComponent.vue";
import { userStore } from "@/store/userStore";
import { computed, onMounted, ref } from "vue";

const page = ref(0);
const store = userStore();

const savedQuoteList = computed(function () {
  return store.getMySavedQuoteList;
});

onMounted(async () => {
  await store.getMySavedQuote();
});
</script>

<style scoped>
.container {
  height: 100%;
  width: 100%;

  border-radius: 10px;
  background-color: #f4f4f4;
  box-shadow: 0px 16px 40px rgba(112, 128, 144, 0.2);
}

.header {
  height: 3%;
  position: relative;

  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: column;
}

v-container {
  padding: 0rem;
}
</style>
