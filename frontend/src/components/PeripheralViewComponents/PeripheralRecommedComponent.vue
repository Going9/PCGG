<template>
  <div>
    <div v-for="(item, index) in listData" :key="index">
      <div class="listitem" :class="{ open: expandedItem === index }">
        <img :src="item['image_source']" alt="noimage!" class="itemimg" />
        <v-divider class="border-opacity-100" vertical></v-divider>
        <div class="itemsummary" @click="toggleReview(index, item)">
          <div class="summary">
            <div>
              <p>제품명: {{ item["name"] }}</p>
            </div>
            <div>
              <p>최고가: {{ item["hprice"] }}원</p>
            </div>
            <div>
              <p>최저가: {{ item["lprice"] }}원</p>
            </div>
          </div>
          <div class="summary">
            <div>
              <p>브랜드: {{ item["brand"] }}</p>
            </div>
            <div>
              <v-btn :href="item['link']">상품 페이지</v-btn>
            </div>
          </div>
        </div>
        <v-divider
          class="border-opacity-100"
          vertical
          v-if="user.loginActivated"
        ></v-divider>
        <v-btn
          icon="$vuetify"
          class="itembtn"
          variant="text"
          @click="saveData(item['id'])"
          v-if="user.loginActivated"
        >
          <img :src="appendIcon" alt="no" class="append" />
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { appendIcon } from "@/assets/Icon";
import { usePeripehralStore } from "@/store/peripheralStore";
import { userStore } from "@/store/userStore";

const store = usePeripehralStore();

const user = userStore();

const listData = computed(function () {
  return store.recommendPeripheral.map((item) => {
    return item;
  });
});

const saveData = (id) => {
  const data = [store.peripheralCategory, id];
  store.saveItem(data);
};
</script>

<style scoped>
.listitem {
  display: flex;
  margin: 2rem 3rem 0rem 3rem;
  align-items: center;
  background-color: #d9d9d9;
  border-radius: 10px;
  padding: 1rem;
  transition: border-radius 0.3s ease-in-out;
}

.listitem.open {
  border-radius: 10px 10px 0rem 0rem;
}

.itemimg {
  height: 5rem;
  margin-right: 3rem;
}

.itemsummary {
  display: flex;
  padding: 3px 1rem;
  width: 80%;
  cursor: pointer;
  justify-content: space-between;
}

.summary {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-right: 2rem;
}

.itembtn {
  margin-left: 3rem;
}
.append {
  width: 100%;
}

.reviews {
  display: flex;
  padding: 0.5rem 1rem 0rem 1rem;
  justify-content: space-between;
}

.review {
  max-height: 0;
  transition: max-height 0.3s ease-in-out, border-color 0.3s ease-in-out;
  overflow: hidden;
  margin: 0rem 3rem;
  background-color: #fff;
  border: solid 1px #000000;
  border-radius: 0rem 0rem 1rem 1rem;
  border-top: none;
  border-color: #00000000;
}

.review.open {
  max-height: 1000px;
}

.review-list {
  display: block;
  padding: 1rem 1rem 0rem 1rem;
}

.review-item {
  height: 2rem;
}

.review-input {
  margin-top: 0.5rem;
  padding: 0rem 1rem 1rem 1rem;
  background-color: #d9d9d9;
}

.review-star {
  margin-top: 0.3rem;
}
</style>
