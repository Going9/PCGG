import { defineStore } from "pinia";

export const usePeripehralStore = defineStore("peripheral", {
  state: () => ({
    peripheralCategory: "keyboard",
    recommendPeripheral: [],
    peripheralList: [],
  }),
  getters: {},
  actions: {
    isPeripheralCategory(value) {
      this.peripheralCategory = value;
    },
  },
});
