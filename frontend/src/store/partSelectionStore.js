import { defineStore } from "pinia";

export const userStore = defineStore("partSelectionStore", {
  state: () => ({
    loginActivated: false,
    accessToken: "",
    triggerOne: true,
    triggerTwo: true,
    peripheralSwitch: true,
  }),
  getters: {
    isLogin: (state) => {
      return state.loginActivated;
    },
    getAccessToken: (state) => {
      return state.accessToken;
    },
    // getLogon: (state) => !state.loginActivated,
  },
  actions: {
    async login(loginInput) {
      await loginAPI(
        loginInput,
        ({ data }) => {
          console.log(data.token)
          this.loginActivated = true;
          this.accessToken = data.token;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    logout() {
      this.loginActivated = false;
      this.accessToken = "";
    },
    triggerActivation() {
      this.triggerOne = !this.triggerOne;
    },
    changePageLoginSignup() {
      this.triggerTwo = !this.triggerTwo;
    },
    isPeripheralRecommend() {
      this.peripheralSwitch = !this.peripheralSwitch;
    },
  },
  // 위 3개가 끝임.
});
