<template>
  <div class="container">
    <div class="selectone">
      <div class="main-select">
        <V-btn
          class="btn-main"
          @click="choiceLaptop = true"
          :class="{ active: choiceLaptop }"
          variant="flat"
        >
          <div>
            <p>노트북</p>
            <img :src="laptopWIcon" alt="noimage" v-if="choiceLaptop" />
            <img :src="laptopIcon" alt="noimage" v-else />
          </div>
        </V-btn>
        <V-btn
          class="btn-main"
          @click="choiceLaptop = false"
          :class="{ active: !choiceLaptop }"
          variant="flat"
        >
          <div>
            <p>데스크탑</p>
            <img :src="desktopIcon" alt="noimage" v-if="choiceLaptop" />
            <img :src="desktopWIcon" alt="noimage" v-else />
          </div>
        </V-btn>
      </div>
      <div class="usage">
        <h3>용도</h3>
        <div class="sub-select">
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceUsage = 'docs'"
            :class="{ active: choiceUsage == 'docs' }"
          >
            <div>
              <p>사무용</p>
              <img
                :src="docsWIcon"
                alt="noimage"
                v-if="choiceUsage == 'docs'"
              />
              <img :src="docsIcon" alt="noimage" v-else />
            </div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceUsage = 'game'"
            :class="{ active: choiceUsage == 'game' }"
          >
            <div>
              <p>게이밍</p>
              <img
                :src="gameWIcon"
                alt="noimage"
                v-if="choiceUsage == 'game'"
              />
              <img :src="gameIcon" alt="noimage" v-else />
            </div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceUsage = 'video'"
            :class="{ active: choiceUsage == 'video' }"
          >
            <div>
              <p>영상편집</p>
              <img
                :src="videoWIcon"
                alt="noimage"
                v-if="choiceUsage == 'video'"
              />
              <img :src="videoIcon" alt="noimage" v-else />
            </div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceUsage = 'three'"
            :class="{ active: choiceUsage == 'three' }"
          >
            <div>
              <p>3d 디자인</p>
              <img
                :src="threeWIcon"
                alt="noimage"
                v-if="choiceUsage == 'three'"
              />
              <img :src="threeIcon" alt="noimage" v-else />
            </div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceUsage = 'broadcast'"
            :class="{ active: choiceUsage == 'broadcast' }"
          >
            <div>
              <p>개인방송</p>
              <img
                :src="broadcastWIcon"
                alt="noimage"
                v-if="choiceUsage == 'broadcast'"
              />
              <img :src="broadcastIcon" alt="noimage" v-else />
            </div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceUsage = 'develop'"
            :class="{ active: choiceUsage == 'develop' }"
          >
            <div>
              <p>고성능개발</p>
              <img
                :src="developWIcon"
                alt="noimage"
                v-if="choiceUsage == 'develop'"
              />
              <img :src="developIcon" alt="noimage" v-else />
            </div>
          </V-btn>
        </div>
      </div>
    </div>
    <div class="selecttwo">
      <div class="budget">
        <h3>예산</h3>
        <v-text-field label="" variant="outlined" suffix="원"></v-text-field>
      </div>
      <div class="os" v-if="choiceLaptop">
        <h3>운영체체 설치</h3>
        <div class="sub-select">
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceOs = 'ok'"
            :class="{ active: choiceOs == 'ok' }"
          >
            <div>
              <img :src="OWIcon" alt="noimage" v-if="choiceOs == 'ok'" />
              <img :src="OBIcon" alt="noimage" v-else />
            </div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceOs = 'no'"
            :class="{ active: choiceOs == 'no' }"
          >
            <div>
              <img :src="XWIcon" alt="noimage" v-if="choiceOs == 'no'" />
              <img :src="XBIcon" alt="noimage" v-else />
            </div>
          </V-btn>
        </div>
      </div>
      <div class="priority">
        <h3>우선순위</h3>
        <v-slider
          v-model="priorityValue"
          thumb-label
          step="50"
          show-ticks
          track-color="#FC794F"
          track-fill-color="#4599fc"
          :thumb-color="color"
          :min="-100"
          :max="100"
        ></v-slider>
      </div>
      <div class="as">
        <h3>AS여부</h3>
        <div class="sub-select">
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceAs = !choiceAs"
            :class="{ active: choiceAs }"
          >
            <div class="as-value">필수</div>
          </V-btn>
        </div>
      </div>
      <div class="size" v-if="!choiceLaptop">
        <h3>케이스 크기</h3>
        <div class="sub-select">
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceSize = 'XL'"
            :class="{ active: choiceSize == 'XL' }"
          >
            <div class="as-value">XL</div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceSize = 'L'"
            :class="{ active: choiceSize == 'L' }"
          >
            <div class="as-value">L</div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceSize = 'M'"
            :class="{ active: choiceSize == 'M' }"
          >
            <div class="as-value">M</div>
          </V-btn>
          <V-btn
            class="btn-sub"
            variant="flat"
            @click="choiceSize = 'S'"
            :class="{ active: choiceSize == 'S' }"
          >
            <div class="as-value">S</div>
          </V-btn>
        </div>
      </div>
    </div>

    <v-divider
      class="border-opacity-100"
      style="padding: 0rem 2rem"
    ></v-divider>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { desktopIcon } from "@/assets/Icon";
import { laptopIcon } from "@/assets/Icon";
import { docsIcon } from "@/assets/Icon";
import { gameIcon } from "@/assets/Icon";
import { videoIcon } from "@/assets/Icon";
import { threeIcon } from "@/assets/Icon";
import { broadcastIcon } from "@/assets/Icon";
import { developIcon } from "@/assets/Icon";
import { desktopWIcon } from "@/assets/Icon";
import { laptopWIcon } from "@/assets/Icon";
import { docsWIcon } from "@/assets/Icon";
import { gameWIcon } from "@/assets/Icon";
import { videoWIcon } from "@/assets/Icon";
import { threeWIcon } from "@/assets/Icon";
import { broadcastWIcon } from "@/assets/Icon";
import { developWIcon } from "@/assets/Icon";
import { OWIcon } from "@/assets/Icon";
import { OBIcon } from "@/assets/Icon";
import { XWIcon } from "@/assets/Icon";
import { XBIcon } from "@/assets/Icon";

const choiceLaptop = ref(true);

const choiceUsage = ref(null);

const choiceOs = ref(null);

const priorityValue = ref(0);

const color = computed(() => {
  if (priorityValue.value > 0) return "#FC794F";
  if (priorityValue.value < 0) return "#4599fc";
  return "white";
});

const choiceAs = ref(null);

const choiceSize = ref(null);
</script>

<style scoped>
.container {
  margin-top: 2rem;
}

.selectone {
  display: flex;
  margin: 2%;
  justify-content: space-between;
}

.main-select {
  display: flex;
  width: 25%;
  margin-right: 2%;
  justify-content: space-between;
}

.btn-main {
  display: flex;
  height: 10rem !important;
  width: 10rem;
  box-shadow: 2px 2px 5px #00000050;
}

.btn-main.active {
  background-color: #4599fc;
  color: white;
  box-shadow: inset 2px 2px 5px #00000050;
}

.btn-main p {
  font-size: large;
  font-weight: bolder;
}

.btn-main img {
  height: 2rem;
}

.sub-select {
  display: flex;
  justify-content: space-evenly;
}

.usage {
  width: 60%;
  margin-left: 2%;
}

.btn-sub {
  display: flex;
  height: 7rem !important;
  width: 7rem;
  box-shadow: 2px 2px 5px #00000050;
}

.btn-sub.active {
  background-color: #4599fc;
  color: white;
  box-shadow: inset 2px 2px 5px #00000050;
}

.btn-sub p {
  font-weight: bolder;
  margin-bottom: 1rem;
}

.btn-sub img {
  height: 2rem;
}

.selecttwo {
  margin: 2%;
  display: flex;
}

.budget {
  width: 25%;
  padding-right: 2%;
}

.os {
  width: 25%;
  padding-right: 2%;
}

.priority {
  width: 25%;
  padding-right: 2%;
}

.as {
  width: 15%;
  padding-right: 2%;
}

.as-value {
  font-size: x-large;
  font-weight: bold;
}

.size {
  width: 35%;
  padding-right: 2%;
}
</style>
