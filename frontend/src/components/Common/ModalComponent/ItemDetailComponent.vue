<script setup>
  import {defineProps, ref, onMounted} from 'vue'
  import { partSelectionStore } from '@/store/partSelectionStore';
  const { selectedItem } = defineProps(['selectedItem']);
  const store = partSelectionStore()
  const partCategory = ref("")

  onMounted(()=>{
    partCategory.value = store.getPartCategory;
  })

</script>

<template>
  <v-container>
    <v-row>
      <img
      class="part-img"
      :src="selectedItem.imageSource" alt="PartImg">
    </v-row>
    <v-row v-if="partCategory == 'cpu'">
      <div class="part-info">
        <v-col cols="12" class="ml-3">
          <h5>
            제품명 : {{ selectedItem.name }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            가 격 : {{ selectedItem.price }}원
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            단종 여부 :
            <span v-if="selectedItem.extinct"> 단종 </span>
            <span v-else> 구매가능 </span>
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            소켓 구분 : {{ selectedItem.socketInfo }}
          </h5>
        </v-col>
        <v-col cols="11" class="ml-3">
          <h5>
            메모리 규격 :
            <span v-if="selectedItem.ddr4">ddr4 </span>
            <span v-if="selectedItem.ddr5">ddr5 </span>
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            내장 그래픽 :
            <span v-if="selectedItem.integratedGraphics"> 포함 </span>
            <span v-else> 미포함 </span>
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            기본 쿨러 :
            <span v-if="selectedItem.coolerIncluded "> 포함 </span>
            <span v-else> 미포함 </span>
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            singleScore : {{ selectedItem.singleScore }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            multiScore : {{ selectedItem.multiScore }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3" v-if="selectedItem.classColumn ">
          <h5>
            메모리 채널 : {{ selectedItem.classColumn }}
          </h5>
        </v-col>
      </div>
    </v-row>
    <v-row v-if="partCategory == 'cooler'">
      <div class="part-info">
        <v-col cols="12" class="ml-3">
          <h5>
            제품명 : {{ selectedItem.name }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            가 격 : {{ selectedItem.price }}원
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            단종 여부 :
            <span v-if="selectedItem.extinct"> 단종 </span>
            <span v-else> 구매가능 </span>
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            타입 : {{ selectedItem.form }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3" v-if="selectedItem.freeWarrantyPeriod>0">
          <h5>
            A/S기간 : {{ selectedItem.freeWarrantyPeriod }}년
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3" >
          <h5>
            높이 : {{ selectedItem.height }}mm
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3" >
          <h5>
            팬 개수 : {{ selectedItem.fanCount }}개
          </h5>
        </v-col>
        <v-col cols="12" class="ml-3" >
          <h5>
            최대 팬소음 : {{ selectedItem.maxFanNoise }}dBA
          </h5>
        </v-col>
      </div>
    </v-row>
    <v-row v-if="partCategory == 'mainboard'">
      <div class="part-info">
        <v-col cols="12" class="ml-3">
          <h5>
            제품명 : {{ selectedItem.name }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            가 격 : {{ selectedItem.price }}원
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            단종 여부 :
            <span v-if="selectedItem.extinct"> 단종 </span>
            <span v-else> 구매가능 </span>
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            CPU 소켓 : {{ selectedItem.socketInfo }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            세부 칩셋 : {{ selectedItem.grade }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            메모리 : {{ selectedItem.memorySpec }}
          </h5>
        </v-col>
        <v-col cols="5" class="ml-3">
          <h5>
            폼펙터 : {{ selectedItem.size }}
          </h5>
        </v-col>
        <v-col cols="12" class="ml-3">
          <h5>
            PCle버전 :
            <span v-if="selectedItem.pcie3">pcie3 </span>
            <span v-if="selectedItem.pcie4">pcie4 </span>
            <span v-if="selectedItem.pcie5">pcie5 </span>
          </h5>
        </v-col>
        <v-col cols="12" class="ml-3">
          <h5>
            M.2 SSD 슬롯 개수 : {{ selectedItem.m2Count }}개
          </h5>
        </v-col>
      </div>
      </v-row>
    <v-row v-if="partCategory == 'ram'">
    <div class="part-info">
      <v-col cols="12" class="ml-3">
        <h5>
          제품명 : {{ selectedItem.name }}
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          가 격 : {{ selectedItem.price }}원
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          단종 여부 :
          <span v-if="selectedItem.extinct"> 단종 </span>
          <span v-else> 구매가능 </span>
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          제품 분류 : {{ selectedItem.memorySpec }}
        </h5>
      </v-col>
      <v-col cols="12" class="ml-3">
        <h5>
          동작클럭(대역폭) : {{ selectedItem.memoryClock }}MHz
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          히트싱크 :
          <span v-if="selectedItem.heatSink"> 포함 </span>
          <span v-else> 미포함 </span>
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          메모리용량: {{ selectedItem.capacity }}G
        </h5>
      </v-col>
      </div>
    </v-row>
    <v-row v-if="partCategory == 'gpu'">
    <div class="part-info">
      <v-col cols="12" class="ml-3">
        <h5>
          제품명 : {{ selectedItem.name }}
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          가 격 : {{ selectedItem.price }}원
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          단종 여부 :
          <span v-if="selectedItem.extinct"> 단종 </span>
          <span v-else> 구매가능 </span>
        </h5>
      </v-col>
      <v-col cols="12" class="ml-3">
        <h5>
          정격파워 : {{ selectedItem.neededPower }}W
        </h5>
      </v-col>
      <v-col cols="6" class="ml-3">
        <h5>
          가로(길이) : {{ selectedItem.width }}mm
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          두께 : {{ selectedItem.thickness }}mm
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          score : {{ selectedItem.score }}점
        </h5>
      </v-col>
      </div>
      </v-row>
    <v-row v-if="partCategory == 'ssd'">
    <div class="part-info">
      <v-col cols="12" class="ml-3">
        <h5>
          제품명 : {{ selectedItem.name }}
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          가 격 : {{ selectedItem.price }}원
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          단종 여부 :
          <span v-if="selectedItem.extinct"> 단종 </span>
          <span v-else> 구매가능 </span>
        </h5>
      </v-col>
      <v-col cols="12" class="ml-3">
        <h5 >
          용량 :
          <span v-if="1 <= selectedItem.capacity">
            {{ selectedItem.capacity }}TB
          </span>
          <span v-else>
            {{ selectedItem.capacity*1000 }}GB
          </span>
        </h5>
      </v-col>
      <v-col cols="6" class="ml-3">
        <h5>
          읽기속도 : {{ selectedItem.readingSpeed }}MB/s
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          제조사 : {{ selectedItem.manufacturer }}
        </h5>
      </v-col>
      <v-col cols="6" class="ml-3">
        <h5>
          쓰기속도 : {{ selectedItem.writingSpeed }}MB/s
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          PCIE버전 : {{ selectedItem.pcieVer }}
        </h5>
      </v-col>
    </div>
    </v-row>
    <v-row v-if="partCategory == 'chassis'">
    <div class="part-info">
      <v-col cols="12" class="ml-3">
        <h5>
          제품명 : {{ selectedItem.name }}
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          가 격 : {{ selectedItem.price }}원
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          단종 여부 :
          <span v-if="selectedItem.extinct"> 단종 </span>
          <span v-else> 구매가능 </span>
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          너비(W) : {{ selectedItem.width }}mm
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          깊이(D) : {{ selectedItem.depth }}mm
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          파워 장착 : {{ selectedItem.maxPowerDepth }}mm
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          GPU 장착 : {{ selectedItem.maxGpuDepth }}mm
        </h5>
      </v-col>
      <v-col cols="12" class="ml-3">
        <h5>
          쿨러 장착 : {{ selectedItem.maxCoolerDepth }}mm
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          Extended-ATX :
          <span v-if="selectedItem.extendedAtx">O</span>
          <span v-else>X</span>
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          Micro-ATX :
          <span v-if="selectedItem.microAtx">O</span>
          <span v-else>X</span>
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          표준-ATX :
          <span v-if="selectedItem.standardAtx">O</span>
          <span v-else>X</span>
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          Mini-ITX :
          <span v-if="selectedItem.miniItx">O</span>
          <span v-else>X</span>
        </h5>
      </v-col>
    </div>
    </v-row>
    <v-row v-if="partCategory == 'power'">
    <div class="part-info">
      <v-col cols="12" class="ml-3">
        <h5>
          제품명 : {{ selectedItem.name }}
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          가 격 : {{ selectedItem.price }}원
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          단종 여부 :
          <span v-if="selectedItem.extinct"> 단종 </span>
          <span v-else> 구매가능 </span>
        </h5>
      </v-col>
      <v-col cols="12" class="ml-3">
        <h5>
          제품 분류 : {{ selectedItem.size }} 파워
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          정격출력 : {{ selectedItem.output }}W
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          깊이 : {{ selectedItem.depth }}mm
        </h5>
      </v-col>
      <v-col cols="5" class="ml-3">
        <h5>
          무상 A/S : {{ selectedItem.freeWarrantyPeriod }}년
        </h5>
      </v-col>
    </div>
    </v-row>

  </v-container>
</template>

<style scoped>
v-container {
  max-width: 100%;
}
 .part-img {
  width: 80%;
  height: 50%;
 }

 .part-info {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  height: 50%;
  border-radius: 0.625rem;
  border: 1px solid #000;
  text-align: start;
 }
</style>
