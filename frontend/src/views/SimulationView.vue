<template>
  <div class="container">
    <div class="banner">
      <img :src="simulationbannerImg" alt="" />
      <div class="banner-text">
        <p>시뮬레이션에서는 부품간의 호환성을</p>
        <p>확인할 수 있습니다</p>
      </div>
    </div>
    <div class="selectpart">
      <div class="select">
        <PartSelectionComponent @partList="setList" />
      </div>
    </div>
    <div class="btnpart">
      <v-btn
        rounded="lg"
        color="#4599fc"
        class="realbtn"
        @click="simulationOn()"
      >
        호환성 검사
      </v-btn>
      <v-dialog v-model="dialog" activator="parent" width="auto">
        <v-card>
          <v-card-text>
            {{ showResult }}
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" block @click="dialog = false">Close</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { simulationbannerImg } from "@/assets/image";
import PartSelectionComponent from "@/components/Common/PartSelectionComponent.vue";
import { useSimulationStore } from "@/store/simulationStore";

const store = useSimulationStore();

const partList = ref([]);

const result = ref({});

const showResult = ref(null);

const setList = (lst) => {
  partList.value = lst.value;
};

const dialog = ref(false);

const simulationOn = async () => {
  await store.goSimulate(partList.value);
  result.value = store.simulateResult;
  if (result.value[0] != undefined) {
    console.log(result.value);
    showResult.value = result.value[0]["detail"];
  } else {
    console.log("OK");
    showResult.value = "모든 부품이 호환됩니다.";
  }
};
</script>

<style scoped>
.container {
  height: 100%;
  width: 100%;
}

.banner {
  position: relative; /* 부모 요소에 상대 위치 설정 */
  height: 20em;
  width: 100vw;
}

.banner img {
  height: 20rem;
  width: 100%;
}

.banner-text {
  position: absolute;
  bottom: 30%;
  left: 0;
  padding: 10px;
  width: 100%;
  text-align: center;
  color: #ffffff;
  font-size: 2rem;
  font-weight: bold;
}

.banner p {
  margin: 0;
  text-shadow: 2px 2px 5px #00000080;
}

.selectpart {
  display: flex;
  justify-content: center;
  margin-top: 3rem;
}

.select {
  width: 75%;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border-radius: 2rem;
}

.select .input {
  margin: 5% 6%;
  width: 80%;
}

.btnpart {
  display: flex;
  justify-content: center;
}

.realbtn {
  height: 5rem;
  width: 50vw;
  margin: 2rem;
  font-size: x-large;
  font-weight: bolder;
}
</style>
