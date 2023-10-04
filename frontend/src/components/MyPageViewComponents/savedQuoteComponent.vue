<template>
  <div class="container">
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
          v-for="post in savedQuoteList"
          :key="post.id"
          style="{{ margin-top: 1%;}}"
          >
      <savedQuoteDetailComponent 
      :post="post"
      />
        </v-col>
    </v-row>
   </v-container>
  </div>
</template>


<script setup>
  import savedQuoteDetailComponent from '@/components/MyPageViewComponents/savedQuoteDetailComponent.vue';
  import { userStore } from '@/store/userStore'
  import { onMounted, ref } from "vue"

  const savedQuoteList = ref("");
  const page = ref(0);
  const store = userStore();

  onMounted(async () => {
    await store.getMySavedQuote();
    savedQuoteList.value = store.getMySavedQuoteList;
    console.log(savedQuoteList.value);
  });

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

v-container {
  padding: 0rem;
}

</style>
