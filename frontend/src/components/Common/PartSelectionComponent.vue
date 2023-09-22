<template>
  <div>
    <ModalComponent
      @closeModal="closeModal"
      :isModal="isModal"
      :selectedPart="partCategory"
    />
    <v-container>
      <v-row>
        <v-col
        cols="12"
        xxl="2"
        xl="3"
        lg="4"
        md="6"
        sm="12"
        v-for="part in partList" :key="part.name">
          <SelectedPartButtonComponent
            @click="toggleModal(part.name)"
            v-if="isPartSelected(part)"
            :props="part.name"
            :value="part.value"
          />
          <PartButtonComponent
            @click="toggleModal(part.name)"
            v-else
            :props="part.name"
          />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup>
  import PartButtonComponent from './PartButtonComponent.vue';
  import SelectedPartButtonComponent from './SelectedPartButtonComponent.vue'
  import ModalComponent from './ModalComponent.vue';
  import { ref } from 'vue';

  let partCategory = "";
  const isModal = ref(false);
  const partList = ref([
  { name: 'CPU', value: "" },
  { name: '쿨러', value: "" },
  { name: '메인보드', value: "" },
  { name: '메모리', value: "" },
  { name: 'GPU', value: "" },
  { name: 'SSD', value: "" },
  { name: 'HDD', value: "" },
  { name: '케이스', value: "" },
  { name: '파워', value: "" },
]);

// 모달을 키고 거기서 선택한 값을 가져옴
function toggleModal(part) {
  isModal.value = !isModal.value;
  partCategory = part;
}

// 모달을 끄고 모달에서 선택된 부품을 리스트에 수정하는 함수를 불러옵니다.
function closeModal(selectedPart) {
  changePart(partCategory, selectedPart);
  isModal.value = false;
}

// partList에 선택된 부품을 추가
function changePart(part, selectedPartName) {
  const idx = partList.value.findIndex((item) => item.name === part);
  partList.value[idx].value = selectedPartName;
}


// 부품이 선택되었는지 여부를 확인하는 함수
function isPartSelected(part) {
  return partList.value.find(item => item.name === part.name && item.value !== "");
}
</script>

<style scoped>
</style>
