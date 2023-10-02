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

// 내 주변기기 가져오기
async function getMyPeripheralAPI(category, success, fail) {
  await apiAuth.get(`/users/${category}`).then(success).catch(fail);
}

// 내 주변기기 삭제하기
async function deleteMyPeripheralAPI(peripheral, success, fail) {
  await apiAuth.delete(`/peripherals/${peripheral}`).then(success).catch(fail);
}

// 내 공유마당 글 가져오기
async function getMyShareAPI(success, fail) {
  await apiAuth.get("/users/share").then(success).catch(fail);
}

// 내 좋아요 공유마당 글 가져오기
async function getMyShareLikeAPI(success, fail) {
  await apiAuth.get("/users/sharelike").then(success).catch(fail);
}

export {
  signupAPI,
  loginAPI,
  getUserInfoAPI,
  getMyPeripheralAPI,
  deleteMyPeripheralAPI,
  getMyShareAPI,
  getMyShareLikeAPI,
};
