import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

// 회원가입
async function signupAPI(user, success, fail) {
  await api.post("/users", JSON.stringify(user)).then(success).catch(fail);
}

// 로그인
async function loginAPI(user, success, fail) {
  await api.post("/auth", JSON.stringify(user)).then(success).catch(fail);
}

// 마이페이지(userInfo)
async function getUserInfoAPI(success, fail) {
  await apiAuth.get("/users").then(success).catch(fail);
}

export { signupAPI, loginAPI, getUserInfoAPI };