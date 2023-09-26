import { isCallEstimatePc } from "@/api/estimateAPI";
import { isCallEstimateLaptop } from "@/api/estimateAPI";
import { defineStore } from "pinia";

export const useEstimateStore = defineStore("estimate", {
  state: () => ({
    recommendEstimate: [],
  }),
  getters: {},
  actions: {
    async callEstimatePc(value) {
      await isCallEstimatePc(
        value,
        ({ data }) => {
          console.log(data);
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
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
});
