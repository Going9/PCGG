<template>
  <div>
    <div v-for="(image, index) in temps" :key="index">
      <div class="listitem" :class="{ open: expandedItem === index }">
        <img :src="image" alt="noimage!" class="itemimg" />
        <v-divider class="border-opacity-100" vertical></v-divider>
        <div class="itemsummary" @click="toggleReview(index)">
          <div class="summary">
            <div>
              <p>제품명: {{ store.peripheralCategory }}</p>
            </div>
            <div>
              <p>최고가: {{ store.peripheralCategory }}</p>
            </div>
            <div>
              <p>최저가: {{ store.peripheralCategory }}</p>
            </div>
          </div>
          <div class="summary">
            <div>
              <p>브랜드: {{ store.peripheralCategory }}</p>
            </div>
            <div>
              <p>단종여부: {{ store.peripheralCategory }}</p>
            </div>
            <div>
              <p>링크: {{ store.peripheralCategory }}</p>
            </div>
          </div>
        </div>
        <v-divider class="border-opacity-100" vertical></v-divider>
        <v-btn icon="$vuetify" class="itembtn" variant="text">
          <img :src="appendIcon" alt="no" class="append" />
        </v-btn>
      </div>
      <div class="review" :class="{ open: expandedItem === index }">
        <!-- 리뷰 내용 -->
        <div class="review-list">
          <div class="review-item">
            <img src="" alt="profile" />
            <span>testdev1</span>
            <span>키보드 좋아요.</span>
          </div>
          <div class="review-item">
            <img src="" alt="profile" />
            <span>testdev1</span>
            <span>키보드 좋아요.</span>
          </div>
          <div class="review-item">
            <img src="" alt="profile" />
            <span>testdev1</span>
            <span>키보드 좋아요.</span>
          </div>
        </div>
        <div class="review-input">
          <v-text-field></v-text-field>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUpdated, ref } from "vue";
import temp1 from "@/assets/temp1.png";
import { appendIcon } from "@/assets/Icon";
import { usePeripehralStore } from "@/store/peripheralStore";

const store = usePeripehralStore();

const temps = [temp1, temp1, temp1, temp1, temp1];

const page = ref(0);

const callRecommend = () => {
  console.log("call Recommend");
};

const callList = () => {
  console.log("call List");
};

const expandedItem = ref(-1);

const toggleReview = (index) => {
  if (expandedItem.value === index) {
    expandedItem.value = -1;
  } else {
    expandedItem.value = index;
  }
};

onMounted(() => {
  page.value = 0;
  callRecommend();
  callList();
});

onUpdated(() => {
  page.value = 0;
  callRecommend();
  callList();
});
</script>

<style scoped>
.listitem {
  display: flex;
  margin: 2rem 3rem 0rem 3rem;
  align-items: center;
  background-color: #d9d9d9;
  border-radius: 10px; /* 초기 상태의 보더 라운드 스타일 */
  padding: 1rem;
  transition: border-radius 0.3s ease-in-out; /* 보더 라운드에 애니메이션 적용 */
}

.listitem.open {
  border-radius: 10px 10px 0rem 0rem; /* 리뷰가 열린 경우의 보더라운드 스타일 */
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
}

.summary {
  margin-right: 2rem;
}

.itembtn {
  margin-left: 3rem;
}
.append {
  width: 100%;
}

.review {
  max-height: 0;
  transition: max-height 0.3s ease-in-out, border-color 0.3s ease-in-out; /* border-color에 트랜지션 추가 */
  overflow: hidden;
  margin: 0rem 3rem;
  background-color: #fff;
  border: solid 1px #000000;
  border-radius: 0rem 0rem 1rem 1rem;
  border-top: none;
  border-color: rgba(0, 0, 0, 0); /* 초기 상태의 보더 색상 */
}

.review.open {
  max-height: 1000px;
  border-color: rgba(0, 0, 0, 1); /* 열린 상태의 보더 색상 (투명) */
}

.review-list {
  display: block;
  padding: 1rem 1rem 0rem 1rem;
}

.review-item {
  height: 2rem;
}

.review-input {
  height: 3rem;
}
</style>
