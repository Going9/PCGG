import { defineStore } from "pinia";
import { loadShareListAPI } from "../api/shareAPI";

export const shareStore = defineStore("shareStore", {
  state: () => ({
    shareList : [],
    page : 0,
    maxPage : 0,
    q : "",
    isLoading : false,
  }),
  getters: {
    getShareList: (state) => {
      return state.shareList;
    },
    getPage: (state) => {
      return state.page
    }
  },
  actions: {
    async loadShareList() {
      if(this.isLoading){
        return
      }else{
        this.isLoading = true
      }
      const data = {
        page : this.page,
        q : this.q}
      await loadShareListAPI(
        data,
        ({ data }) => {
          this.shareList.push(...data.content)
          if(data.content.length > 0){
            this.maxPage = this.page
          }
          setTimeout(()=>{
            this.isLoading = false;
          },100)
        },
        (error) => {
          console.log(error);
          this.isLoading = false;
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
      this.q = ""
      this.shareList = []
      this.page = 0
      this.maxPage = 0
      this.loadShareList()
    },
    search(value){
      this.q = value
      this.page = 0
      this.maxPage = 0
      this.shareList = []
      this.loadShareList()
    }
  },
});
