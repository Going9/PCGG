import { defineStore } from "pinia";
import { usedMarketAPI } from "@/api/usedmarketAPI";
import router from "@/router";

export const usedMarketStore = defineStore("usedMarketStore", {

    actions: {
        async createPost(UsedMarketInput) {
            await usedMarketAPI(
                usedMarketInput,
                ({ data }) => {
                    router.push({ name: "UsedMarket"});
                },
                (error) => {
                    console.log(error);
                }
            );
        },       
    },
});