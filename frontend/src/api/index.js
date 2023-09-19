import axios from "axios";
import { userStore } from "@/store/userStore";

import { ref } from "vue";
// const accessToken = ref("");
const store = userStore();
const accessToken = store.getAccessToken;
// const accessToken = "Bearer".concat(" ", userStore().getAccessToken);
// accessToken.value = userStore().getAccessToken;

function apiSpringInstance() {
  const instance = axios.create({
    baseURL: import.meta.env.VITE_ENV_VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    // 쿠키에 정보(토큰)을 저장하기 위해 필수로 설정
    // withCredentials: true,
  });
  return instance;
}

function apiSpringAuthInstance() {
  const instance = axios.create({
    baseURL: import.meta.env.VITE_ENV_VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      Authorization: toString(accessToken),
    },
  });
  return instance;
}

export { apiSpringInstance, apiSpringAuthInstance };
