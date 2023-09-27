import { isCallPart } from "@/api/partRecommendAPI";
import { defineStore } from "pinia";

export const usePartRecommendStore = defineStore("partrecommend", {
  state: () => ({
    recommendPart: [],
  }),
  getters: {},
  actions: {
    async callPart(value) {
      await isCallPart(
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
