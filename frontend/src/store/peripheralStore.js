import { isCallPeripheralList, isSaveMyPeripheral } from "@/api/peripheralAPI";
import { isCreatePeripheralReview } from "@/api/peripheralAPI";
import { isCallPeripheralReview } from "@/api/peripheralAPI";
import { isUpdatePeripheralReview } from "@/api/peripheralAPI";
import { isDeletePeripheralReview } from "@/api/peripheralAPI";
import { defineStore } from "pinia";

export const usePeripehralStore = defineStore("peripheral", {
  state: () => ({
    peripheralCategory: "keyboard",
    recommendPeripheral: [],
    peripheralList: [],
    reviewList: [],
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
        },
        (error) => {
          console.log(error);
        }
      );
    },
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
