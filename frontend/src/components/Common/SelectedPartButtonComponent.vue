<script setup>
  import { defineProps, defineEmits, ref } from 'vue';
  import { WhitePartIcon } from '@/assets/Icon';
  const props = defineProps({
    selectedItem: String,
    category : String,
});
</script>

<template>
  <div class="container">
      <v-container>
        <v-row class="box">
          <v-col cols="2">
            <div class="icon">
                 <img :src="WhitePartIcon" alt="WhitePartIcon">
            </div>
          </v-col >
          <v-col cols="7">
            <div class="font">
              <div>{{ category }}</div>
            </div>
          </v-col>
          <v-col cols="12">
            <div class="part-font">
              <div>
                {{ selectedItem }}
              </div>
            </div>
          </v-col>
        </v-row>
      </v-container>

  </div>
</template>

<style scoped>
.container {
 width: 16rem;
 height: 10rem;

background-color: #4599FC;
stroke-width: 1px;
stroke: #A8A8A8;
box-shadow: 2px 2px 5px 0px rgba(0, 0, 0, 0.30) inset;
}

.box, .container {
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}

.icon {
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}

.font {
  color: var(--Color, #FFF);
  text-align: center;
  font-family: Lato;
  font-size: 1.5rem;
  font-style: normal;
  font-weight: 700;
  line-height: 100%; /* 1.5rem */

  display: flex;
  align-items: flex-start;
  justify-content: flex-start;

}

.part-font {
  color: var(--Color, #FFF);
  text-align: center;
  font-family: Lato;
  font-size: 1.5rem;
  font-style: normal;
  font-weight: 700;
  line-height: 100%; /* 1.5rem */

  display: flex;
  align-items: center;
  justify-content: center;
}
</style>


