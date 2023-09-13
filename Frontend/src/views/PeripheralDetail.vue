<template>
  <div class="container">
    <div class="detail">
      <div class="line-one">
        <div class="product-profile">
          <img :src="temp1" alt="임시" />
          <p>정말 비싼 키보드</p>
        </div>
        <div class="price">
          <img :src="data1" alt="price" />
        </div>
      </div>
      <div class="line-two">
        <div class="stock">
          <p>최저가 추이</p>
          <img :src="data2" alt="stock" />
        </div>
        <div class="spec">
          <p>제품사양</p>
          <img :src="data3" alt="spec" />
        </div>
      </div>
    </div>
    <hr />
    <h3>이런 상품도 있습니다 !</h3>
    <div class="anohter">
      <img :src="anohter1" alt="딴거1번" />
      <img :src="anohter2" alt="딴거2번" />
      <img :src="anohter3" alt="딴거3번" />
      <img :src="anohter4" alt="딴거4번" />
    </div>
    <hr />
    <div class="review"></div>
    <div>
      <v-btn @click="goList">목록으로</v-btn>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import temp1 from "@/assets/temp1.png";
import anohter1 from "@/assets/anohter.png";
import anohter2 from "@/assets/anohter2.png";
import anohter3 from "@/assets/anohter3.png";
import anohter4 from "@/assets/anohter4.png";
import data1 from "@/assets/data1.png";
import data2 from "@/assets/data2.png";
import data3 from "@/assets/data3.png";

const goList = () => {
  console.log("hey?");
  router.push({ name: "Peripheral" });
};
</script>

<style scoped>
.container {
  margin-top: 5%;
  margin-left: 5%;
  margin-right: 5%;
  display: flex;
  position: relative;
  height: 90%;
  width: 90%;
  flex-direction: column;
}

.detail {
  width: 100%;
}

.line-one,
.line-two {
  display: flex;
  justify-content: space-around;
}

.product-profile {
  height: 30%;
}

.product-profile img {
  height: 225px;
}

.price {
  height: 30%;
}
.price img {
  height: 225px;
}

.stock {
  height: 30%;
}
.stock img {
  height: 225px;
}

.spec {
  height: 30%;
}
.spec img {
  height: 225px;
}

.anohter {
  width: 100%;
  display: flex;
  justify-content: center;
}

.anohter img {
  height: 225px;
}

.review {
  width: 100%;
}
</style>
