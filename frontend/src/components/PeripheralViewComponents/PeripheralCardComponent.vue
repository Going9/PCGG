<template>
  <div>
    <div v-for="(item, index) in listData" :key="index">
      <div class="listitem" :class="{ open: expandedItem === index }">
        <img :src="item['imageSource']" alt="noimage!" class="itemimg" />
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
      <div class="review" :class="{ open: expandedItem === index }">
        <!-- 리뷰 내용 -->
        <div
          v-if="reviewData.length == 0"
          style="display: flex; justify-content: center; margin: 1rem"
        >
          <p>아직 이 상품에 대한 리뷰가 없습니다.</p>
        </div>
        <div v-for="(review, index) in reviewData" :key="index" class="reviews">
          <div style="display: flex; flex-grow: 1" v-if="updating == index">
            <v-select
              v-model="item.reviewRating"
              label="평점"
              :items="[0, 1, 2, 3, 4, 5]"
              variant="underlined"
              hide-details="true"
              style="width: 4rem"
            ></v-select
            ><v-text-field
              v-model="item.reviewUpdateValue"
              variant="underlined"
              label="리뷰 수정"
              hide-details="true"
              @keyup.enter="updatedReview(item, review['reviewId'])"
              style="width: 90%"
            ></v-text-field>
          </div>
          <div v-else>
            <p>{{ review["userNickname"] }} : {{ review["review"] }}</p>
          </div>
          <div style="display: flex; align-items: center">
            <p>평점 : {{ review["rating"] }}</p>
            <div
              v-if="review['userNickname'] == user.userInfo['nickname']"
              style="margin-left: 1rem"
            >
              <v-btn
                variant="text"
                style="height: 1.5rem; width: 1.5rem"
                icon="$vuetify"
                ><img
                  :src="deleteIcon"
                  alt="no"
                  style="height: 1.5rem; width: 1.5rem"
                  @click="deleteReview(item, review['reviewId'])"
              /></v-btn>
              <v-btn
                variant="text"
                style="height: 1.5rem; width: 1.5rem; margin-left: 0.5rem"
                icon="$vuetify"
                ><img
                  :src="editIcon"
                  alt="no"
                  style="height: 1.5rem; width: 1.5rem"
                  @click="updateReview(index)"
              /></v-btn>
            </div>
          </div>
        </div>
        <div class="review-input" v-if="user.loginActivated">
          <v-rating
            v-model="item.reviewRating"
            density="compact"
            hide-details="true"
            class="review-star"
          ></v-rating>
          <v-text-field
            v-model="item.reviewInputValue"
            variant="outlined"
            label="리뷰 입력"
            hide-details="true"
            @keyup.enter="goReview(item)"
          ></v-text-field>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { appendIcon, deleteIcon, editIcon } from "@/assets/Icon";
import { usePeripehralStore } from "@/store/peripheralStore";
import { userStore } from "@/store/userStore";

const store = usePeripehralStore();

const user = userStore();

const listData = computed(function () {
  return store.peripheralList.map((item) => {
    item.reviewRating = 0;
    return item;
  });
});

const reviewData = computed(function () {
  const reviews = store.reviewList;
  if (reviews.length === 0) {
    return [];
  }
  return reviews;
});

const saveData = (id) => {
  const data = [store.peripheralCategory, id];
  store.saveItem(data);
};

const expandedItem = ref(-1);

const toggleReview = (index, item) => {
  if (expandedItem.value === index) {
    expandedItem.value = -1;
  } else {
    expandedItem.value = index;
    const data = { category: store.peripheralCategory, peripheralId: item.id };
    console.log(store.peripheralCategory);
    store.callReview(data);
  }
};

const updating = ref(-1);

const updateReview = (index) => {
  updating.value = index;
};

const updatedReview = async (item, reviewId) => {
  const reviewData = {
    category: store.peripheralCategory,
    reviewId: reviewId,
    peripheralId: item.id,
    rating: item.reviewRating,
    review: item.reviewUpdateValue,
  };
  await store.updateReview(reviewData);
  updating.value = -1;
  const data = { category: store.peripheralCategory, peripheralId: item.id };
  store.callReview(data);
};

const deleteReview = async (item, reviewId) => {
  const reviewData = {
    category: store.peripheralCategory,
    reviewId: reviewId,
  };
  await store.deleteReview(reviewData);
  const data = { category: store.peripheralCategory, peripheralId: item.id };
  store.callReview(data);
};

const goReview = async (item) => {
  const reviewData = {
    category: store.peripheralCategory,
    peripheralId: item.id,
    rating: item.reviewRating,
    review: item.reviewInputValue,
  };
  if (reviewData["review"]) {
    console.log(reviewData);
    await store.createReview(reviewData);
    const data = { category: store.peripheralCategory, peripheralId: item.id };
    store.callReview(data);
  } else {
    console.log("error");
  }
  item.reviewInputValue = "";
  item.reviewRating = 0;
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
  border-color: #000000;
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
