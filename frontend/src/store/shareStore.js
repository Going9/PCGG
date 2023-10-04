import { defineStore } from "pinia";
import { loadShareListAPI } from "../api/shareAPI";

export const shareStore = defineStore("shareStore", {
  state: () => ({
    shareList : [],
    page : 0,
    maxPage : 0
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
          this.shareList.push(...data.content)
          if(data.content.length > 0){
            this.maxPage = this.page
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    increasePage() {
      if(this.maxPage == this.page){
        this.page += 1
        this.loadShareList()
      }
    },
    decreasePage() {
      this.page -= 1
      this.loadShareList()
    },
    resetPage() {
      this.shareList = []
      this.page = 0
      this.maxPage = 0
      this.loadShareList()
    },
  },
});
