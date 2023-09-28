import axios from "axios";
import { setInterceptors } from "./interceptors";

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
    },
  });
  return setInterceptors(instance);
}

export { apiSpringInstance, apiSpringAuthInstance };
