<template>
  <div class="container">
    <div class="selectone">
      <div class="main-select">
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
      </div>
      <div class="usage">
        <h3>용도</h3>
        <div class="usage-combo">
          <v-select
            v-for="(combo, index) in choiceUsage"
            :key="index"
            v-model="combo.selectedItem"
            :items="combo.items.map((item) => item.label)"
            :label="combo.label"
            @click="resetOtherCombos(index)"
            variant="solo"
            class="cleanbtn"
          ></v-select>
        </div>
      </div>
    </div>
    <div class="selecttwo">
      <div class="budget">
        <h3>예산</h3>
        <v-text-field
          label=""
          variant="outlined"
          suffix="원"
          v-model="choiceBudget"
          type="number"
          step="100000"
          @input="filterNegativeNumbers"
        ></v-text-field>
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
        <div style="display: flex; align-items: center">
          <v-slider
            v-model="priorityValue"
            step="1"
            show-ticks="always"
            :ticks="tickLabels"
            track-color="#FC794F"
            track-fill-color="#4599fc"
            :thumb-color="color"
            :min="-1"
            :max="1"
            hide-details
          ></v-slider>
        </div>
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
      <div class="case" v-if="!choiceLaptop">
        <h3>케이스 크기</h3>
        <div class="usage-combo">
          <v-select
            v-for="(combo, index) in choiceCase"
            :key="index"
            v-model="combo.selectedItem"
            :items="combo.items"
            :label="combo.label"
            variant="solo"
          ></v-select>
        </div>
      </div>
      <div class="ssd" v-if="!choiceLaptop">
        <h3>SSD 용량</h3>
        <div class="usage-combo">
          <v-select
            v-for="(combo, index) in choiceSsd"
            :key="index"
            v-model="combo.selectedItem"
            :items="combo.items"
            :label="combo.label"
            variant="solo"
          ></v-select>
        </div>
      </div>
    </div>

    <div class="result">
      <v-btn
        v-if="store.recommendToggle == false"
        color="#DA0000"
        class="callrecommend"
        :loading="isLoading"
        @click="performAction()"
      >
        추천받기</v-btn
      >
      <v-btn
        v-else
        color="#4599fc"
        class="callrecommend"
        @click="resetEstimate()"
      >
        {{ user.userInfo["nickname"] }}님께 추천 드리는 견적</v-btn
      >
    </div>
    <div>
      <div
        v-if="store.recommendToggle == false"
        style="display: flex; justify-content: center; margin-top: 5rem"
      >
        <p style="font-size: larger; font-weight: bolder">
          추천받은 내용이 없습니다.
        </p>
      </div>
      <div v-else>
        <div v-for="(item, index) in listData" :key="index" class="recoitem">
          <img :src="item.chassis.imageSource" alt="noimg" />
          <v-divider class="border-opacity-100" vertical></v-divider>
          <div class="summary">
            <div class="recosummary1">
              <p>CPU : {{ item.cpu["name"] }}</p>
              <p>GPU : {{ item.gpu["name"] }}</p>
              <p>메인보드 : {{ item.mainboard["name"] }}</p>
              <p>파워 : {{ item.power["name"] }}</p>
            </div>
            <div class="recosummary2">
              <p>케이스 : {{ item.chassis["name"] }}</p>
              <p>ram : {{ item.ram["name"] }}</p>
              <p>ssd : {{ item.ssd }}</p>
              <p>예상 비용: {{ item.totalPrice }}</p>
            </div>
          </div>
          <v-dialog v-model="item.dialog" activator="parent" width="auto">
            <v-card>
              <v-card-text>
                <h3>CPU</h3>
                <div style="display: flex">
                  <img :src="item.cpu['imageSource']" alt="noimg" />
                  <div>
                    <p>제품명 : {{ item.cpu["name"] }}</p>
                    <p>소켓정보 : {{ item.cpu["socketInfo"] }}</p>
                    <p>
                      램 호환성 :
                      <span v-if="item.cpu['ddr4'] == true">ddr4</span
                      ><span v-if="item.cpu['ddr5'] == true"> ddr5</span>
                    </p>
                    <p>내장그래픽 : {{ item.cpu["integratedGraphics"] }}</p>
                    <p>쿨러 포함 : {{ item.cpu["coolerIncluded"] }}</p>
                    <p>싱글 벤치 : {{ item.cpu["singleScore"] }}점</p>
                    <p>멀티 벤치 : {{ item.cpu["multiScore"] }}점</p>
                    <p>가격 : {{ item.cpu["price"] }}원</p>
                  </div>
                </div>
                <h3>GPU</h3>
                <div style="display: flex">
                  <img :src="item.gpu['imageSource']" alt="noimage" />
                  <div>
                    <p>제품명 : {{ item.gpu["name"] }}</p>
                    <p>필요 전력 : {{ item.gpu["neededPower"] }}W</p>
                    <p>
                      사이즈 : {{ item.gpu["width"] }}cm X
                      {{ item.gpu["thickness"] }}cm
                    </p>
                    <p>벤치 점수 : {{ item.gpu["score"] }}점</p>
                    <p>가격 : {{ item.gpu["price"] }}원</p>
                  </div>
                </div>
                <h3>메인보드</h3>
                <div style="display: flex">
                  <img :src="item.mainboard['imageSource']" alt="" />
                  <div>
                    <p>제품명 : {{ item.mainboard["name"] }}</p>
                    <p>소켓정보 : {{ item.mainboard["socketInfo"] }}</p>
                    <p>제품명 : {{ item.mainboard["name"] }}</p>
                    <p>제품명 : {{ item.mainboard["name"] }}</p>
                    <p>제품명 : {{ item.mainboard }}</p>
                  </div>
                </div>
                <h3>파워</h3>
                <div style="display: flex">
                  <div>
                    <p>제품명 : {{ item.power }}</p>
                  </div>
                </div>
                <h3>케이스</h3>
                <div style="display: flex">
                  <div>
                    <p>제품명 : {{ item.chassis }}</p>
                  </div>
                </div>
                <h3>ram</h3>
                <div style="display: flex">
                  <div>
                    <p>제품명 : {{ item.ram }}</p>
                  </div>
                </div>
                <h3>ssd</h3>
                <div style="display: flex">
                  <div>
                    <p>제품명 : {{ item.ssd }}</p>
                  </div>
                </div>
              </v-card-text>
              <v-card-actions>
                <v-btn color="primary" block @click="dialog = false"
                  >Close Detail</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-divider
            class="border-opacity-100"
            vertical
            v-if="user.loginActivated"
          ></v-divider>
          <v-btn
            icon="$vuetify"
            class="itembtn"
            variant="text"
            @click="saveEstimate(item)"
            v-if="user.loginActivated"
          >
            <img :src="appendIcon" alt="no" class="append" />
          </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { desktopIcon } from "@/assets/Icon";
import { laptopIcon } from "@/assets/Icon";
import { desktopWIcon } from "@/assets/Icon";
import { laptopWIcon } from "@/assets/Icon";
import { OWIcon } from "@/assets/Icon";
import { OBIcon } from "@/assets/Icon";
import { XWIcon } from "@/assets/Icon";
import { XBIcon } from "@/assets/Icon";
import { appendIcon } from "@/assets/Icon";
import { useEstimateStore } from "@/store/estimateStore";
import { userStore } from "@/store/userStore";

const store = useEstimateStore();
const user = userStore();

const listData = computed(function () {
  return store.recommendEstimate.map((item) => {
    return { ...item, dialog: false };
  });
});

const choiceLaptop = ref(false);

const choiceUsage = ref([
  {
    selectedItem: null,
    items: [
      { value: "가성비사무", label: "가성비 사무" },
      { value: "고성능사무", label: "고성능 사무" },
    ],
    label: "사무용",
  },
  {
    selectedItem: null,
    items: [
      { value: "캐주얼게임", label: "캐주얼 게임" },
      { value: "중사양게임", label: "중사양 게임" },
      { value: "고사양게임", label: "고사양 게임" },
    ],
    label: "게이밍",
  },
  {
    selectedItem: null,
    items: [
      { value: "일반영상편집", label: "일반" },
      { value: "전문영상편집", label: "전문가용" },
    ],
    label: "영상편집",
  },
  {
    selectedItem: null,
    items: [{ value: "3D디자인", label: "3D 디자인" }],
    label: "3D 디자인",
  },
  {
    selectedItem: null,
    items: [
      { value: "일반방송", label: "일반 방송" },
      { value: "캐주얼게임방송", label: "캐주얼 게임" },
      { value: "고성능게임방송", label: "고사양 게임" },
    ],
    label: "개인방송",
  },
  {
    selectedItem: null,
    items: [{ value: "고사양개발", label: "고성능 개발" }],
    label: "고성능 개발",
  },
]);

const resetOtherCombos = (currentIndex) => {
  choiceUsage.value.forEach((combo, index) => {
    if (index !== currentIndex) {
      combo.selectedItem = null;
    }
  });
};

const choiceBudget = ref("");

const filterNegativeNumbers = () => {
  if (choiceBudget.value < 0) {
    choiceBudget.value = 0;
  }
};

const budgetValue = computed(() => {
  return Number(choiceBudget.value);
});

const choiceOs = ref("ok");

const priorityValue = ref(0);

const tickLabels = {
  "-1": "성능",
  0: "가성비",
  1: "가격",
};

const color = computed(() => {
  if (priorityValue.value < 0) return "#FC794F";
  if (priorityValue.value > 0) return "#4599fc";
  return "white";
});

const choiceAs = ref(null);

const choiceCase = ref([
  {
    selectedItem: null,
    items: ["M-ATX", "ATX", "M-ITX"],
    label: "케이스 크기",
  },
]);

const choiceSsd = ref([
  {
    selectedItem: null,
    items: ["128GB", "256GB", "512GB", "1TB", "2TB", "4TB", "8TB"],
    label: "SSD 용량",
  },
]);

const estimate = ref([]);

const isLoading = ref(false);

const addEstimate = () => {
  if (choiceLaptop.value) {
    estimate.value.push("laptop");
  } else {
    estimate.value.push("desktop");
  }

  choiceUsage.value.forEach((combo) => {
    if (combo.selectedItem) {
      const selectedItemLabel = combo.selectedItem;
      const matchingItem = combo.items.find(
        (item) => item.label === selectedItemLabel
      );
      if (matchingItem) {
        const selectedItemValue = matchingItem.value;
        estimate.value.push(selectedItemValue);
      }
    }
  });

  estimate.value.push(budgetValue.value);

  if (choiceOs.value == "ok") {
    estimate.value.push(true);
  } else {
    estimate.value.push(false);
  }

  estimate.value.push(priorityValue.value);

  if (choiceAs.value) {
    estimate.value.push(true);
  } else {
    estimate.value.push(false);
  }

  choiceCase.value.forEach((combo) => {
    if (combo.selectedItem) {
      estimate.value.push(combo.selectedItem);
    }
  });

  choiceSsd.value.forEach((combo) => {
    if (combo.selectedItem == "128GB") {
      estimate.value.push(100);
    } else if (combo.selectedItem == "256GB") {
      estimate.value.push(200);
    } else if (combo.selectedItem == "512GB") {
      estimate.value.push(500);
    } else if (combo.selectedItem == "1TB") {
      estimate.value.push(1000);
    } else if (combo.selectedItem == "2TB") {
      estimate.value.push(2000);
    } else if (combo.selectedItem == "4TB") {
      estimate.value.push(4000);
    } else {
      estimate.value.push(8000);
    }
  });
  if (estimate.value.length >= 7) {
    if (estimate.value[0] == "laptop") {
      store.callEstimateLaptop(estimate.value);
    }
  }

  if (estimate.value.length == 8) {
    if (estimate.value[0] == "desktop") {
      store.callEstimatePc(estimate.value);
    }
  }

  console.log(estimate.value);
  estimate.value = [];
  listData.value = store.recommendEstimate;
};

const performAction = async () => {
  try {
    isLoading.value = !store.recommendToggle;
    await addEstimate();
  } catch (error) {
    console.error(error);
    isLoading.value = store.recommendToggle;
  }
};

const resetEstimate = () => {
  isLoading.value = false;
  store.recommendToggle = false;
  store.recommendEstimate = [];
  console.log(store.recommendEstimate);
  console.log(store.recommendToggle);
};

const saveEstimate = (item) => {
  const data = {
    cpuId: item.cpu["id"],
    mainboardId: item.mainboard["id"],
    ssdId: null,
    ramId: item.ram["id"],
    gpuId: item.gpu["id"],
    chassisId: item.chassis["id"],
    powerId: item.power["id"],
    coolerId: null,
    userId: user.userInfo["userid"],
  };
  store.saveEstimate(data);
};

const dialog = ref(false);
</script>

<style scoped>
::v-deep(.v-field__append-inner) {
  display: none;
}

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

.usage-combo {
  display: flex;
  height: 100%;
}

.cleanbtn {
  max-width: 15% !important;
  margin-right: 1%;
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
  justify-content: space-evenly;
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

.case {
  width: 15%;
  padding-right: 2%;
}

.ssd {
  width: 15%;
  padding-right: 2%;
}

.result {
  margin: 0% 2%;
}

.callrecommend {
  height: 4rem !important;
  width: 100%;
  font-size: large;
  font-weight: bolder;
}

.recoitem {
  height: 7rem;
  display: flex;
  justify-content: space-between;
  margin: 2%;
  background-color: #d9d9d9;
  border-radius: 10px;
  align-items: center;
  padding: 0% 2%;
}

.recoitem img {
  height: 6rem;
  margin-right: 1rem;
  border-radius: 10px;
}

.summary {
  width: 80%;
  display: flex;
  justify-content: space-between;
  margin: 0rem 1rem;
}

.recosummary1 {
  height: 6rem;
  max-width: 45%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.recosummary2 {
  height: 6rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.itembtn {
  margin-left: 1rem;
}

.itembtn img {
  margin-right: 0rem;
}
.append {
  width: 100%;
  height: 100% !important;
}
</style>
