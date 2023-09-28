<template>
  <main>
    <div class="container">
      <div class="banner">
        <img alt="BannerImg" class="bannerImg" :src="rankingbannerImg" />
        <div class="box">
          <div class="bannerComment">
            <span>견적 랭킹에서는 가장 많이 판매되는 제품을 확인하세요.</span>
          </div>
        </div>
      </div>
      <div class="maincarouselbox">
        <Carousel3D />
      </div>
      <div class="subcarouselbox" ref="subCarouselBox">
        <div>
          <div class="subCarouselComment">
            <span>닉네임님에게 추천하는 상품</span>
          </div>
          <div>
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
import { rankingbannerImg } from "@/assets/image";
import debounce from "lodash/debounce";

const subCarouselBox = ref(null);
let isIntersecting = ref(false);

const onIntersect = debounce((entries) => {
  const entry = entries[0];
  console.log(entry.intersectionRatio);
  if (entry.intersectionRatio > 0.8 && entry.intersectionRatio != 1) {
    isIntersecting.value = true;
  } else {
    isIntersecting.value = false;
  }
}, 300);

onMounted(() => {
  const options = {
    threshold: 0.8,
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
.container {
  width: 100%;
  /* height: rem; */
  height: 120rem;
}
.maincarouselbox {
  margin-top: 2%;
  margin-bottom: 25%;
}

.subcarouselbox {
  width: 100%;
  height: 500px;
  /* margin: 25%; */
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

