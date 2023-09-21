<script setup>
import PartSelectionComponent from '@/components/Common/PartSelectionComponent.vue';
</script>

<template>
  <div class="container">

    <div class="header">
      <span class="main-title">
        <h1>
          견적공유 글쓰기
        </h1>
      </span>
      <span class="button">
        <v-btn
          color="blue"
          text="완료"
        ></v-btn>
      </span>
    </div>
    <div class="content">

      <div class="sub-title">
        <h2>
          제목
        </h2>
      </div>
      <div class="title-input input">
        <v-text-field
          label="제목"
          :rules="rules"
          hide-details="auto"
        ></v-text-field>
      </div>
      <div class="sub-title">
        <h2>
          부품 선택
        </h2>
      </div>
      <div class="select">
        <PartSelectionComponent/>
        <!-- <div class="select-input input">
          <v-text-field
            label="CPU"
            :rules="rules"
            hide-details="auto"
          ></v-text-field>
        </div>
        <div class="select-input input">
          <v-text-field
            label="GPU"
            :rules="rules"
            hide-details="auto"
          ></v-text-field>
        </div>
        <div class="select-input input">
          <v-text-field
            label="케이스"
            :rules="rules"
            hide-details="auto"
          ></v-text-field>
        </div>
        <div class="select-input input">
          <v-text-field
            label="RAM"
            :rules="rules"
            hide-details="auto"
          ></v-text-field>
        </div>
        <div class="select-input input">
          <v-text-field
            label="그래픽 카드"
            :rules="rules"
            hide-details="auto"
          ></v-text-field>
        </div> -->
      </div>

      <div class="sub-title">
        <h2>
          한 줄 설명
        </h2>
      </div>
      <div class="info-input input">
        <v-text-field
          label="한 줄 설명"
          :rules="rules"
          hide-details="auto"
        ></v-text-field>
      </div>
    </div>

  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;

}

.header {
    width: 100%;
    margin: 2% 10%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 1rem;
    border-bottom: 0.1rem solid black;
}


.content {
  display: flex;
    width: 100%;
    flex-direction: column;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
}

.sub-title {
  width: 64%;
  text-align:left;
  margin: 2%;
  padding-bottom: 1%;
  border-bottom: 1px solid black;
}
.input {
  margin: 0 5% 5%;
  width: 64%;
}

.select {
  width: 64%;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  /* border: 1px solid rgba(187, 181, 181, 0.667); */
  border-radius: 2rem;
}

.select .input {
  margin: 5% 6%;
  width: 80%;

}

</style>
