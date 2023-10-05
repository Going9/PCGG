<script setup>
import { defineProps, defineEmits, ref, onMounted, onUpdated } from 'vue';
import SearchBarComponent from './SearchBarComponent.vue';
import ResultListComponent from './ModalComponent/ResultListComponent.vue';
import { partSelectionStore } from '@/store/partSelectionStore'

const props = defineProps({
  isModal: Boolean,
  partCategory : String,
  label : String
});

const partList = ref([]);
const store = partSelectionStore();
const selectedPart = ref("");
const q = ref("")
const emit = defineEmits(['closeModal']);

onUpdated(() => {
  store.isPartCategory(props.label)
  callPartList()
  })

const callPartList = () => {
  const data = {
      q : q.value,
    }
    store.loadPartList(data);
    setTimeout(()=>{
      partList.value = store.getlist;
    },100)
}

const search = (value) => {
  q.value = value
  store.search(value)
  callPartList()
  setTimeout(()=>{
    partList.value = store.getlist;
  },200)
}

// 선택된 부품 정보를 partSelectionComponent로 보냄
const closeModal = () => {
  if(selectedPart.value != ""){
    const newSelectedPart = selectedPart.value
    emit('closeModal', newSelectedPart);
    selectedPart.value = ""
  }
  else{
    const newSelectedPart = {
    name : "", id: -1}
    emit('closeModal', newSelectedPart);
  }
};
const setItem = (item) => {
  selectedPart.value = item
};

</script>
<template>
  <v-row justify="center">
    <v-dialog
      v-model="props.isModal"
      fullscreen
      :scrim="false"
      transition="dialog-bottom-transition"
    >
      <v-card>
        <v-toolbar
          dark
          color="primary"
        >
          <v-btn
            icon
            dark
            @click="closeModal"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>

          <v-toolbar-title>부품 선택 ( {{partCategory}} )</v-toolbar-title>
          <v-toolbar-items class="selectedpart">
            <h2>
              선택된 부품 : {{ selectedPart.name != "" ? selectedPart.name : " 없음"}}
            </h2>
          </v-toolbar-items>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn
              variant="text"
              @click="closeModal"
            >
              Save
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        <SearchBarComponent
         @q="search"/>
        <ResultListComponent
        :partList="partList"
        @increase="callPartList"
        @msg="setItem"/>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<style scoped>

.selectedpart {
  display: flex;
  align-items: center;
}

.dialog-bottom-transition-enter-active,
.dialog-bottom-transition-leave-active {
  transition: transform .2s ease-in-out;
}
</style>
