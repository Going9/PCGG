<template>
  <Carousel
    :autoplay="5000"
    :itemsToShow="3.95"
    :wrapAround="true"
    :transition="500"
  >
    <Slide v-for="slide in 5" :key="slide">
      <div class="carousel__item">
        <!-- {{ slide }} -->
        <!-- <img src="@/assets/laptopImg.jpg" alt="laptopImg" width="400" height="200"> -->
        <div class="item">
          <BestSeller :item="top5List[slide-1]"/>
        </div>
      </div>
    </Slide>
    <template #addons>
      <Pagination />
      <Navigation />
    </template>
  </Carousel>
</template>

<script setup>
import BestSeller from "@/components/RecommendationViewComponents/BestSellerComponent.vue";
import { Carousel, Navigation, Slide, Pagination } from "vue3-carousel";
import {onMounted, ref} from "vue"
import {loadShareTop5API} from "@/api/shareAPI"


const top5List = ref([])
const settings = {
  itemsToShow: 1,
  snapAlign: "center",
};

const loadShareTop5 = () =>{
  loadShareTop5API(
    ({ data }) => {
      console.log(data)
      top5List.value = data},
    (error) => {
          console.log(error);
        }
  )
}

onMounted(() => {
  loadShareTop5();
})

const breakpoints = {
  700: {
    itemsToShow: 3.5,
    snapAlign: "center",
  },
  1024: {
    itemsToShow: 5,
    snapAlign: "start",
  },
};
</script>

<style scoped>
.item {
}
.carousel__slide {
  padding: 5px;
}

.carousel__viewport {
  perspective: 2000px;
}

.carousel__track {
  transform-style: preserve-3d;
}

.carousel__slide--sliding {
  transition: 0.5s;
}

.carousel__slide {
  opacity: 0.9;
  transform: rotateY(-20deg) scale(0.9);
}

.carousel__slide--active ~ .carousel__slide {
  transform: rotateY(20deg) scale(0.9);
}

.carousel__slide--prev {
  opacity: 1;
  transform: rotateY(-10deg) scale(0.95);
}

.carousel__slide--next {
  opacity: 1;
  transform: rotateY(10deg) scale(0.95);
}

.carousel__slide--active {
  opacity: 1;
  transform: rotateY(0) scale(1.1);
}
</style>
