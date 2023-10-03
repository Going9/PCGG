<template>
  <div>
    <ModalComponent
      @closeModal="closeModal"
      :isModal="isModal"
      :partCategory="partCategory"
      :label="label"
    />
    <v-container>
      <v-row>
        <v-col
        cols="12"
        xxl="2"
        xl="3"
        lg="3"
        md="4"
        sm="6"
        v-for="part in partList" :key="part.name">
          <SelectedPartButtonComponent
            @click="toggleModal(part.name)"
            v-if="isPartSelected(part)"
            :category="part.name"
            :selectedItem="part.value"
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
  import { ref, defineEmits, onUpdated} from 'vue';

  const emit = defineEmits(['partList']);

  onUpdated(() => {
    const newPartList = partList;
    emit('partList', newPartList);
  })

  const partCategory = ref("");
  const label = ref("cpu");
  const isModal = ref(false);
  const partList = ref([
  { name: 'CPU', value: "", label: 'cpu', id: -1 },
  { name: '쿨러', value: "", label: 'cooler', id: -1 },
  { name: '메인보드', value: "", label: 'mainboard', id: -1 },
  { name: '메모리', value: "", label: 'ram', id: -1 },
  { name: 'GPU', value: "", label: 'gpu', id: -1 },
  { name: 'SSD', value: "", label: 'ssd', id: -1 },
  { name: '케이스', value: "", label: 'chassis', id: -1 },
  { name: '파워', value: "", label: 'power', id: -1 },
]);

const computedLabel = () => {
  const filteredPart = partList.value.find(part => part.name === partCategory.value);
  return filteredPart ? filteredPart.label : '';
};

// 모달을 키고 거기서 선택한 값을 가져옴
function toggleModal(part) {
  isModal.value = !isModal.value;
  partCategory.value = part;
  label.value = computedLabel();
}

// 모달을 끄고 모달에서 선택된 부품을 리스트에 수정하는 함수를 불러옵니다.
function closeModal(selectedPart) {
  changePart(partCategory, selectedPart);
  isModal.value = false;
}

// partList에 선택된 부품을 추가
function changePart(part, selectedPart) {
  const idx = partList.value.findIndex((item) => item.name === part.value);
  partList.value[idx].value = selectedPart.name;
  partList.value[idx].id = selectedPart.id;

}


// 부품이 선택되었는지 여부를 확인
function isPartSelected(part) {
  return partList.value.find(item => item.name === part.name && item.value !== "");
}
</script>

<style scoped>
</style>
