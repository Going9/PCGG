import {
  isCallPeripheralRecommend,
  isCallPeripheralList,
  isSaveMyPeripheral,
  isDeletePeripheralReview,
  isUpdatePeripheralReview,
  isCallPeripheralReview,
  isCreatePeripheralReview,
} from "@/api/peripheralAPI";
import { defineStore } from "pinia";

export const usePeripehralStore = defineStore("peripheral", {
  state: () => ({
    peripheralCategory: "keyboard",
    recommendPeripheral: [],
    peripheralList: [],
    reviewList: [],
    searchResults: [],
    searchToggle: false,
  }),
  getters: {
    getlist: (state) => {
      return state.peripheralList;
    },
  },
  actions: {
    // 카테고리 바꾸기
    isPeripheralCategory(value) {
      this.peripheralCategory = value;
      this.peripheralList = [];
    },

    // 제품검색
    isSearchPeripheral(searchString) {
      console.log(searchString);
      if (searchString) {
        searchString = searchString.toLowerCase();
        const searchResults = this.peripheralList.filter((item) => {
          return (
            item.name.toLowerCase().includes(searchString) ||
            item.brand.toLowerCase().includes(searchString)
          );
        });
        this.searchResults = searchResults;
        this.searchToggle = true;
      } else {
        this.searchToggle = false;
        return;
      }
    },

    // 검색 초기화
    isSearchInit() {
      this.searchToggle = false;
    },

    // 추천받기
    async callRecommend(value) {
      await isCallPeripheralRecommend(
        value,
        ({ data }) => {
          this.recommendPeripheral.push(data);
          console.log("추천");
          console.log(this.recommendPeripheral);
        },
        (error) => {
          console.log(value);
          console.log(error);
        }
      );
    },

    // 전체목록 불러오기
    async callList(value) {
      await isCallPeripheralList(
        value,
        ({ data }) => {
          this.peripheralList.push(...data.content);
          console.log("목록");
          console.log(this.peripheralList);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 이하 리뷰 생성/조회/수정/삭제
    async createReview(value) {
      await isCreatePeripheralReview(
        value,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async callReview(value) {
      await isCallPeripheralReview(
        value,
        ({ data }) => {
          this.reviewList = [];
          this.reviewList.push(...data.reviewDtos.content);
          console.log(this.reviewList);
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async updateReview(value) {
      await isUpdatePeripheralReview(
        value,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async deleteReview(value) {
      await isDeletePeripheralReview(
        value,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 마이페이지에 저장
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
