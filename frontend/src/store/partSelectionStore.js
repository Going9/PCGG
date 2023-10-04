import { defineStore } from "pinia";
import { loadPartListAPI } from "@/api/partAPI";

export const partSelectionStore = defineStore("partSelectionStore", {
  state: () => ({
    partCategory: "cpu",
    partList: [],
    page : 0,
    maxPage : 0,
    q : "",
    isLoading : false,
  }),
  getters: {
    getlist: (state) => {
      return state.partList;
    },
  },
  actions: {
    // 처음 부품을 선택하면 초기값으로 초기화 후 리스트를 다시 불러옴
    isPartCategory(value) {
      this.q = "";
      this.page = 0;
      this.maxPage = 0;
      this.partCategory = value;
      this.partList = [];
      this.loadPartList()
    },
    async loadPartList() {
      if(this.isLoading){
        return
      }else{
        this.isLoading = true
      }
      const data = {
        partCategory : this.partCategory,
        q : this.q,
        page : this.page };
      await loadPartListAPI(
        data,
        ({ data }) => {
          this.partList.push(...data.content);
          if(data.content.length > 0){
            this.maxPage = this.page;
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
        this.loadPartList()
      }
    },
    // 새로운 검색어를 입력하면 초기값으로 초기화 후 리스트를 다시 불러옴
    search(value){
      this.q = value
      this.page = 0
      this.maxPage = 0
      this.partList = []
      this.loadPartList()
    }

  },
});
