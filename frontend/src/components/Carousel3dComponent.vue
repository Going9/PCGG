<template>
  <Carousel :autoplay="5000" :itemsToShow="3.95" :wrapAround="true" :transition="500">
    <Slide v-for="slide in 5" :key="slide">
      <div class="carousel__item">
        <!-- {{ slide }} -->
        <!-- <img src="@/assets/laptopImg.jpg" alt="laptopImg" width="400" height="200"> -->
        <div class="item">
          <BestSeller/>
        </div>
      </div>
    </Slide>
    <template #addons>
      <Pagination />
      <Navigation />
    </template>
  </Carousel>
</template>


<script >
import BestSeller from '@/components/BestSellerComponent.vue';
import { defineComponent } from 'vue'
import { Carousel, Navigation, Slide, Pagination } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'
export default defineComponent({
  name: 'ExamplePagination',
  components: {
    Pagination,
    Carousel,
    Slide,
    Navigation,
    BestSeller,
  },
  data: () => ({
    // carousel settings
    settings: {
      itemsToShow: 1,
      snapAlign: 'center',
    },
    // breakpoints are mobile first
    // any settings not specified will fallback to the carousel settings
    breakpoints: {
      // 700px and up
      700: {
        itemsToShow: 3.5,
        snapAlign: 'center',
      },
      // 1024 and up
      1024: {
        itemsToShow: 5,
        snapAlign: 'start',
      },
    },
  }),
})

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

