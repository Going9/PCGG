import {
  isCallEstimatePc,
  isCallEstimateLaptop,
  isSaveEstimate,
} from "@/api/estimateAPI";

import { defineStore } from "pinia";

export const useEstimateStore = defineStore("estimate", {
  state: () => ({
    recommendEstimate: [],
    recommendToggle: false,
  }),
  getters: {},
  actions: {
    async callEstimatePc(value) {
      await isCallEstimatePc(
        value,
        ({ data }) => {
          this.recommendEstimate = [];
          this.recommendEstimate.push(...data);
          console.log(this.recommendEstimate);
          this.recommendToggle = true;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async callEstimateLaptop(value) {
      await isCallEstimateLaptop(
        value,
        ({ data }) => {
          this.recommendEstimate = [];
          this.recommendEstimate.push(...data);
          console.log(this.recommendEstimate);
          this.recommendToggle = true;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async saveEstimate(value) {
      await isSaveEstimate(
        value,
        ({ data }) => {
          console.log(data);
          console.log(value);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
});
