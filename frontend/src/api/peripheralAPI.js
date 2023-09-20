import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

// 주변기기 추천 목록 불러오기
// 추후 추가

// 주변기기 목록 불러오기
async function isCallPeripheralList(success, fail) {
  await api.get("/peripheral").then(success).catch(fail);
}

// 주변기기 디테일 불러오기
async function isCallPeripheralDetail(data, success, fail) {
  await api.get(`/peripheral/${data.deviceId}`).then(success).catch(fail);
}

// 주변기기 후기 조회
async function isCallPeripheralReview(data, success, fail) {
  await api
    .get(`/peripheral/${data.deviceId}/review`)
    .then(success)
    .catch(fail);
}

// 후기 달기
async function isCreatePeripheralReview(data, success, fail) {
  await apiAuth
    .post(`/peripheral/${data.deviceId}/review`)
    .then(success)
    .catch(fail);
}
// 후기 수정
async function isUpdatePeripheralReview(data, success, fail) {
  await apiAuth
    .put(`/peripheral/${data.deviceId}/review`)
    .then(success)
    .catch(fail);
}

// 후기 삭제
async function isDeletePeripheralReview(data, success, fail) {
  await apiAuth
    .delete(`/peripheral/${data.deviceId}/review`)
    .then(success)
    .catch(fail);
}

export {
  isCallPeripheralList,
  isCallPeripheralDetail,
  isCallPeripheralReview,
  isCreatePeripheralReview,
  isDeletePeripheralReview,
  isUpdatePeripheralReview,
};
