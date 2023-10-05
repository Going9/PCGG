import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

// 이메일 인증번호 발송
async function sendToEmailAPI(email, success, fail) {
  await api.post(`/users/${email}`).then(success).catch(fail);
}

// 이메일 인증
async function verifiedCodeAPI(emailCode, success, fail) {
  await api
    .post("/users/email", JSON.stringify(emailCode))
    .then(success)
    .catch(fail);
}

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

async function getMySavedQuoteAPI(success, fail) {
  await apiAuth.get("/users/quotes").then(success).catch(fail);
}

// 회원탈퇴
async function withdrawalAPI(success, fail) {
  await apiAuth.put("/users").then(success).catch(fail);
}

export {
  sendToEmailAPI,
  verifiedCodeAPI,
  signupAPI,
  loginAPI,
  getUserInfoAPI,
  getMyPeripheralAPI,
  deleteMyPeripheralAPI,
  getMyShareAPI,
  getMyShareLikeAPI,
  getMySavedQuoteAPI,
  withdrawalAPI,
};
