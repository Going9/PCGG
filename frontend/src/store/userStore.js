// Utilities
// 먼저 피니아에서 스토어를 가져와 줘야한다고 함
import { defineStore } from "pinia";
import { loginAPI } from "@/api/userAPI";
import router from "@/router";

// defineStore의 첫번째 인자는 스토어의 이름. 보통 파일 이름과 같이 하면 된다고 함. 이 이름이 나중에 데브툴에서 보이는 이름이라는 듯
export const userStore = defineStore("userStore", {
  // state는 대충 vuex랑 똑같은듯
  state: () => ({
    loginActivated: false,
    accessToken: "",
    triggerOne: true,
    triggerTwo: true,
    peripheralSwitch: true,
  }),
  // getters도 vuex랑 큰 차이점 모르겠음
  getters: {
    isLogin: (state) => {
      return state.loginActivated;
    },
    getAccessToken: (state) => {
      return state.accessToken;
    },
    // getLogon: (state) => !state.loginActivated,
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
          router.push({ name: "Home" });
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
