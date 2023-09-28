import { isCallPeripheralList, isSaveMyPeripheral } from "@/api/peripheralAPI";
// import { isCallPeripheralDetail } from "@/api/peripheralAPI";
// import { isCreatePeripheralReview } from "@/api/peripheralAPI";
// import { isCallPeripheralReview } from "@/api/peripheralAPI";
// import { isUpdatePeripheralReview } from "@/api/peripheralAPI";
// import { isDeletePeripheralReview } from "@/api/peripheralAPI";
import { defineStore } from "pinia";

export const usePeripehralStore = defineStore("peripheral", {
  state: () => ({
    peripheralCategory: "keyboard",
    recommendPeripheral: [],
    peripheralList: [],
  }),
  getters: {
    getlist: (state) => {
      return state.peripheralList;
    },
  },
  actions: {
    isPeripheralCategory(value) {
      this.peripheralCategory = value;
      this.peripheralList = [];
    },
    async callList(value) {
      await isCallPeripheralList(
        value,
        ({ data }) => {
          this.peripheralList.push(...data.content);
          console.log(this.peripheralList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async saveItem(value) {
      await isSaveMyPeripheral(
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
