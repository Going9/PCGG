import { isCallPeripheralList } from "@/api/peripheralAPI";
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
  getters: {},
  actions: {
    isPeripheralCategory(value) {
      this.peripheralCategory = value;
    },
    async callList(value) {
      await isCallPeripheralList(
        value,
        ({ peripheral }) => {
          console.log(value);
          console.log("success");
          console.log(peripheral);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
});
