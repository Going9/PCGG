import { defineStore } from "pinia";
import { loadPartListAPI } from "@/api/partAPI";

export const partSelectionStore = defineStore("partSelectionStore", {
  state: () => ({
    partCategory: "cpu",
    partList: [],
  }),
  getters: {
    getlist: (state) => {
      return state.partList;
    },
  },
  actions: {
    isPartCategory(value) {
      this.partCategory = value;
      this.partList = [];
    },
    async loadPartList(value) {
      await loadPartListAPI(
        value,
        ({ data }) => {
          this.partList = data.content;
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
});
