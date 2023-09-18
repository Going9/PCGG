<template>
  <v-container>


    <v-row class="container">
      <v-col>
        <div class="left">
          <div class="info">
            <div>
              추천상품
            </div>
            <div>
              내용
            </div>
          </div>
        </div>
      </v-col>
      <v-col>
        <div class="right">
          <div class="imgbox">
            <img
            :src="laptopImg"
            alt="laptopImg"

          />
          </div>
        </div>
      </v-col>

    </v-row>
  </v-container>

</template>

<script setup>
  import { laptopImg } from "@/assets/image";
</script>

<style scoped>
  .container {
    width:25rem;
    height:10rem;
    display: flex;

  }

  .left {
    border-radius: 0.5rem;
    background: rgba(217, 217, 217, 0.50);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .right, .left,.imgbox, img {
    width: 100%;
    height: 100%;
    border-radius: 2rem;
  }
  .info {
    color: var(--Color, #FFF);
    text-align: center;
    text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.25);
    font-family: Inter;
    font-size: 2rem;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
  }
</style>
