<script setup>
import { defineProps, defineEmits, ref, onMounted } from 'vue';
import SearchBarComponent from './SearchBarComponent.vue';
import ResultListComponent from './ModalComponent/ResultListComponent.vue';

const props = defineProps({
  isModal: Boolean,
  partCategory : String,
});

const selectedPart = ref("")
const emit = defineEmits(['closeModal']);

// 선택된 부품 정보를 partSelectionComponent로 보냄
const closeModal = () => {
  if(selectedPart.value != ""){
    const newSelectedPart = selectedPart.value.name
    emit('closeModal', newSelectedPart);
    selectedPart.value = ""
  }
  else{
    emit('closeModal', "");
  }
};
const setItem = (itemName) => {
  console.log(itemName)
  selectedPart.value = itemName
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
        <SearchBarComponent/>
        <ResultListComponent
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
