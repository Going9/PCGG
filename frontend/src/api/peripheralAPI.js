import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

// 추천 api는?

// 목록 조회 - 안쓸듯
async function isCallPeripheralList(success, fail) {
  await api.get("/peripheral").then(success).catch(fail);
}

// 상세 조회
async function isCallPeripheralDetail(data, success, fail) {
  await api.get(`/peripheral/${data.divideId}`).then(success).catch(fail);
}

// 후기 작성
async function isCreatePeripheralReview(data, success, fail) {
  await apiAuth
    .post(`/peripheral/${data.divideId}/review`)
    .then(success)
    .catch(fail);
}

// 후기 조회 - 리뷰 아이디가 필요할까?
async function isCallPeripheralReview(data, success, fail) {
  await api
    .get(`/peripheral/${data.divideId}/review/${data.reviewId}`)
    .then(success)
    .catch(fail);
}

// 후기 수정
async function isUpdatePeripheralReview(data, success, fail) {
  await apiAuth
    .put(`/peripheral/${data.divideId}/review`)
    .then(success)
    .catch(fail);
}

// 후기 삭제
async function isDeletePeripheralReview(data, success, fail) {
  await apiAuth
    .delete(`/peripheral/${data.divideId}/review`)
    .then(success)
    .catch(fail);
}

// 제품 저장
async function isSaveMyPeripheral(data, success, fail) {
  await apiAuth.post(`/peripheral/${data.divideId}`).then(success).catch(fail);
}

// 저장된 제품 삭제 - 이건 마이페이지로 가는게 맞지 않을까?
async function isDeleteMyPeripheral(data, success, fail) {
  await apiAuth
    .delete(`/peripheral/${data.divideId}`)
    .then(success)
    .catch(fail);
}

export {
  isCallPeripheralDetail,
  isCallPeripheralList,
  isCallPeripheralReview,
  isCreatePeripheralReview,
  isDeleteMyPeripheral,
  isDeletePeripheralReview,
  isSaveMyPeripheral,
  isUpdatePeripheralReview,
};
