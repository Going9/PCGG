import { defineStore } from "pinia";

export const useSimulationStore = defineStore("simulation", {
  state: () => ({
    partCategory: "CPU",
    partList: [],
  }),
  getters: {},
  actions: {
    isPartCategory(value) {
      this.partCategory = value;
    },
  },
});
