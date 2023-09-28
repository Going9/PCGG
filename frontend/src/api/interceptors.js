import { userStore } from "@/store/userStore";

export function setInterceptors(instance) {
  instance.interceptors.request.use(
    (config) => {
      const token = userStore().getAccessToken;
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  return instance;
}
