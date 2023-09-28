import { defineStore } from "pinia";
import { loadShareListAPI } from "../api/shareAPI";

export const shareStore = defineStore("shareStore", {
  state: () => ({
    shareList : [],
    page : 0
  }),
  getters: {
    isShareList: (state) => {
      return state.shareList;
    },
    isPage: (state) => {
      return state.page
    }
  },
  actions: {
    async loadShareList() {
      const pages = { page : this.page}
      await loadShareListAPI(
        pages,
        ({ data }) => {
          this.shareList = data.content
        },
        (error) => {
          console.log(error);
        }
      );
    },
    increasePage() {
      this.page ++
      this.loadShareList()
    },
    decreasePage() {
      this.page --
      this.loadShareList()
    },
    moveToPage(page) {
      this.page = page
      this.loadShareList()
    },
  },
});
