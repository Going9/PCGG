import axios from "axios";

function apiSpringInstance() {
  const instance = axios.create({
    baseURL: import.meta.env.VITE_ENV_VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { apiSpringInstance };
