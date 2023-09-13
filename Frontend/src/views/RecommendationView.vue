<template>
  <main>
    <div class="container">
      <!-- 상단 배너 -->
      <div class="banner">
        <img
          alt="BannerImg"
          class="bannerImg"
          src="@/assets/ranking-banner.jpg"
        />
        <div class="box">
          <div class="bannerComment">
            <span>견적 랭킹에서는 가장 많이 판매되는 제품을 확인하세요.</span>
          </div>
        </div>
      </div>
      <!-- 메인 캐러셀 -->
      <div class="maincarouselbox">
        <Carousel3D />
      </div>
      <!-- 서브 캐러셀 -->
      <!-- v-if를 통한 가시성 조절 -->
      <div class="subcarouselbox" ref="subCarouselBox">
        <div v-if="isIntersecting">
          <div class="subCarouselComment">
            <span>닉네임님에게 추천하는 상품</span>
          </div>
          <div>
            <!-- 가시성 조절을 위한 v-if -->
            <DoubleCarousel />
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import Carousel3D from "@/components/RecommendationViewComponents/Carousel3dComponent";
import DoubleCarousel from "@/components/RecommendationViewComponents/DoubleCarouselComponent";
import { ref, onMounted } from "vue";
import debounce from "lodash/debounce";

const subCarouselBox = ref(null);
let isIntersecting = ref(false);

const onIntersect = debounce((entries) => {
  const entry = entries[0];
  console.log(entry.intersectionRatio);
  if (entry.intersectionRatio > 0.5) {
    isIntersecting.value = true;
  } else {
    isIntersecting.value = false;
  }
}, 150); // 디바운스 시간 설정

onMounted(() => {
  const options = {
    threshold: 0.5,
  };

  const observer = new IntersectionObserver(onIntersect, options);

  if (subCarouselBox.value) {
    observer.observe(subCarouselBox.value);
  }

  return () => {
    if (subCarouselBox.value) {
      observer.unobserve(subCarouselBox.value);
    }
  };
});
</script>

<style scoped>
.container {
  height: 2800px;
}
.maincarouselbox {
  margin-top: 2%;
  margin-bottom: 25%;
}

.subcarouselbox {
  margin: 25%;
}

.subCarouselComment {
  margin: 5% 0;
  text-align: center;
  font-family: Inter;
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}
</style>

<style scoped>
.container {
  height: 2800px;
}
.maincarouselbox {
  margin-top: 2%;
}

.subcarouselbox {
  margin: 0 0 25%;
}

.subCarouselComment {
  margin: 5% 0;
  text-align: center;
  font-family: Inter;
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}
</style>
