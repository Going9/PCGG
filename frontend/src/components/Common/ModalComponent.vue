<script setup>
import { defineProps, defineEmits } from 'vue';
import SearchBarComponent from './SearchBarComponent.vue';
import ResultListComponent from './ModalComponent/ResultListComponent.vue';

const props = defineProps({
  isModal: Boolean,
});

let selectedPart = ""
// console.log(props.isModal)
const emit = defineEmits(['closeModal']);
const closeModal = () => {
  const newSelectedPart = selectedPart
  emit('closeModal', newSelectedPart); // "closeModal" 이벤트를 발생시켜 부모 컴포넌트로 데이터를 전달
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
          <v-toolbar-title>부품 선택</v-toolbar-title>
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
        <ResultListComponent/>

      </v-card>
    </v-dialog>
  </v-row>
</template>

<style scoped>
.dialog-bottom-transition-enter-active,
.dialog-bottom-transition-leave-active {
  transition: transform .2s ease-in-out;
}
</style>
