<template>
  <div>
    <Carousel
      :transition="200"
      id="thumbnails"
      :items-to-show="3"
      :wrap-around="true"
      v-model="upCurrentSlide"
      ref="carousel"
    >
      <Slide v-for="slide in 10" :key="slide">
        <div class="carousel__item" @click="upSlideTo(slide - 1)">
          <UpItemInfo
          />
        </div>
      </Slide>
    </Carousel>

    <Carousel
      :transition="200"
      id="thumbnails"
      :items-to-show="3"
      :wrap-around="true"
      v-model="downCurrentSlide"
      ref="carousel"
    >
      <Slide v-for="slide in 10" :key="slide">
        <div class="carousel__item" @click="downSlideTo(slide - 1)">
          <DownItemInfo/>
        </div>
      </Slide>
    </Carousel>
  </div>
</template>

<script>
import DownItemInfo from "@/components/RecommendationViewComponents/DownCarouselComponent.vue"
import UpItemInfo from "@/components/RecommendationViewComponents/UpCarouselComponent.vue"
import { defineComponent } from "vue";
import { Carousel, Slide } from "vue3-carousel";

import "vue3-carousel/dist/carousel.css";

export default defineComponent({
  name: "DoubleCarousel",
  components: {
    Carousel,
    Slide,
    // BestSeller,
    UpItemInfo,
    DownItemInfo,
  },
  data: () => ({
    upCurrentSlide: 0,
    downCurrentSlide: 0,
  }),
  methods: {
    upSlideTo(val) {
      this.upCurrentSlide = val;
    },
    downSlideTo(val) {
      this.downCurrentSlide = val;
    },
  },
  created() {
    // downCurrentSlide를 200ms 간격으로 변경
    setInterval(() => {
      // 여기에서 downCurrentSlide를 1씩 증가시킵니다.
      this.upCurrentSlide++;
    }, 1000),
      setInterval(() => {
        // 여기에서 downCurrentSlide를 1씩 증가시킵니다.
        this.downCurrentSlide--;
      }, 1000);
  },
});
</script>
