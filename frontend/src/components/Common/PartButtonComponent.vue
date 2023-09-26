<script setup>
  import {defineProps} from 'vue'
  import { BlackPartIcon } from '@/assets/Icon';
  const { props } = defineProps(['props']);
</script>

<template>
  <div class="container">
      <v-container>
        <v-row class="box">
          <v-col cols="5">
            <div class="icon">
                 <img :src="BlackPartIcon" alt="BlackPartIcon">
            </div>
          </v-col >
          <v-col cols="7">
            <div class="font">
              <div>{{ props }}</div>
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

 border-radius: 0.5rem;
 border: 1px solid #A8A8A8;
 background: rgba(217, 217, 217, 0.00);
 box-shadow: 2px 2px 5px 1px rgba(0, 0, 0, 0.30);
}

.box {
  margin: 0% 3%;
}

.box, .container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.font {
  color: #000;
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


