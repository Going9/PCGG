// Utilities
// 먼저 피니아에서 스토어를 가져와 줘야한다고 함
import { defineStore } from "pinia";
import {
  loginAPI,
  getUserInfoAPI,
  getMyPeripheralAPI,
  deleteMyPeripheralAPI,
  getMyShareAPI,
  getMyShareLikeAPI,
  getMySavedQuoteAPI,
  deleteMySavedQuoteAPI,
} from "@/api/userAPI";
import router from "@/router";

// defineStore의 첫번째 인자는 스토어의 이름. 보통 파일 이름과 같이 하면 된다고 함. 이 이름이 나중에 데브툴에서 보이는 이름이라는 듯
export const userStore = defineStore("userStore", {
  // state는 대충 vuex랑 똑같은듯
  state: () => ({
    loginActivated: false,
    accessToken: "",
    userInfo: {
      userid: "",
      nickname: "",
    },
    triggerOne: true,
    triggerTwo: true,
    mypageCategory: "share",
    peripheralCategory: "keyboard",
    peripheralList: [],
    shareList: [],
    shareLikeList: [],
    savedQuoteList: [],
  }),
  persist: {
    enabled: true,
    strategies: [
      {
        storage: sessionStorage,
        paths: [
          "loginActivated",
          "accessToken",
          "userInfo",
          "mypageCategory",
          "peripheralCategory",
          "peripheralList",
          "shareList",
          "shareLikeList",
          "savedQuoteList",
        ],
      },
    ],
  },
  // getters도 vuex랑 큰 차이점 모르겠음
  getters: {
    isLogin: (state) => {
      return state.loginActivated;
    },
    getAccessToken: (state) => {
      return state.accessToken;
    },
    getUserInfo: (state) => {
      return state.userInfo;
    },
    getCategory: (state) => {
      return state.mypageCategory;
    },
    getPeripheralCategory: (state) => {
      return state.peripheralCategory;
    },
    getperipheralList: (state) => {
      return state.peripheralList;
    },
    getShareList: (state) => {
      return state.shareList;
    },
    getShareLikeList: (state) => {
      return state.shareLikeList;
    },
    getMySavedQuoteList: (state) => {
      return state.savedQuoteList;
    },
  },
  // 이게 actions랑 mutations합친거인듯. 액션에서 뮤테이션 보내고 뮤테이션에서 스테이트 바꾸고 하는게 아니고 한번에 바꿈
  // vuex배울때 이러면 뭔가 문제가 될수 있다 그랬던거 같은데 기억안남
  actions: {
    async login(loginInput) {
      await loginAPI(
        loginInput,
        ({ data }) => {
          this.loginActivated = true;
          this.accessToken = data.token;
        },
        (error) => {
          alert(error.response.data.message);
          console.log(error);
        }
      );
    },
    async loginUser() {
      await getUserInfoAPI(
        ({ data }) => {
          this.userInfo.userid = data.userId;
          this.userInfo.nickname = data.nickname;
          router.push({ name: "Home" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    logout() {
      // 모든 state 값을 일일이 초기화 하는대신
      // session에 있는 값을 제거해보려 했지만 실패
      this.loginActivated = false;
      this.accessToken = "";
      this.userInfo = {
        userid: "",
        nickname: "",
      };
      this.mypageCategory = "share";
      this.peripheralCategory = "keyboard";
      this.peripheralList = [];
      this.shareList = [];
      this.shareLikeList = [];
      this.savedQuoteList = [];
    },
    triggerActivation() {
      this.triggerOne = !this.triggerOne;
    },
    changePageLoginSignup() {
      this.triggerTwo = !this.triggerTwo;
    },
    async setCategory(category) {
      this.mypageCategory = category;
    },
    async setPeripheralCategory(category) {
      this.peripheralCategory = category;
    },
    async getMyPeripheral(category) {
      await getMyPeripheralAPI(
        category,
        ({ data }) => {
          this.peripheralList = data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async deleteMyPeripheral(peripheral) {
      await deleteMyPeripheralAPI(
        peripheral,
        () => {
          console.log("삭제 완료");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getMyShare() {
      await getMyShareAPI(
        ({ data }) => {
          this.shareList = data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getMyShareLike() {
      await getMyShareLikeAPI(
        ({ data }) => {
          this.shareLikeList = data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getMySavedQuote() {
      await getMySavedQuoteAPI(
        ({ data }) => {
          this.savedQuoteList = data.content;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async deleteMySavedQuote(quoteId) {
      await deleteMySavedQuoteAPI(
        quoteId,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
  // 위 3개가 끝임.
});
