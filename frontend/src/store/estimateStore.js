import { isCallEstimate } from "@/api/estimateAPI";
import { defineStore } from "pinia";

export const useEstimateStore = defineStore("estimate", {
  state: () => ({
    recommendEstimate: [],
  }),
  getters: {},
  actions: {
    async callEstimate(value) {
      await isCallEstimate(
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
