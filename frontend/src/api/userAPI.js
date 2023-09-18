import { apiSpringInstance } from "./index.js";

const api = apiSpringInstance();

// 회원가입
async function signup(user, success, fail) {
  await api.post("/users", JSON.stringify(user)).then(success).catch(fail);
}

// 로그인
async function login(user, success, fail) {
  await api.post("/auth", JSON.stringify(user)).then(success).catch(fail);
}

// 테스트 api
async function test(success, fail) {
  await api.get("/users").then(success).catch(fail);
}

export { signup, login, test };
