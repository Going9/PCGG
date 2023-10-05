import {
  apiSpringInstance,
  apiSpringAuthInstance,
  apiDjangoInstance,
} from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();
const apiDjango = apiDjangoInstance();

// 추천 api는?
async function isCallPeripheralRecommend(data, success, fail) {
  await apiDjango
    .get(`/recommends/${data.category}/${data.userId}/?format=json`)
    .then(success)
    .catch(fail);
}

// 목록 조회
async function isCallPeripheralList(data, success, fail) {
  const params = { pages: data.page };
  await api
    .get(`/peripherals/${data.category}`, { params })
    .then(success)
    .catch(fail);
}

// 후기 작성
async function isCreatePeripheralReview(data, success, fail) {
  const body = {
    peripheralId: data.peripheralId,
    rating: data.rating,
    review: data.review,
  };
  console.log(body);
  await apiAuth
    .post(`/peripherals/${data.category}/reviews`, body)
    .then(success)
    .catch(fail);
}

// 후기 조회
async function isCallPeripheralReview(data, success, fail) {
  console.log(`/peripherals/${data.category}/${data.peripheralId}/reviews`);
  await api
    .get(`/peripherals/${data.category}/${data.peripheralId}/reviews`)
    .then(success)
    .catch(fail);
}

// 후기 수정
async function isUpdatePeripheralReview(data, success, fail) {
  const body = {
    peripheralId: data.peripheralId,
    rating: data.rating,
    review: data.review,
  };
  console.log(body);
  await apiAuth
    .put(`/peripherals/${data.category}/reviews/${data.reviewId}`, body)
    .then(success)
    .catch(fail);
}

// 후기 삭제
async function isDeletePeripheralReview(data, success, fail) {
  await apiAuth
    .delete(`/peripherals/${data.category}/reviews/${data.reviewId}`)
    .then(success)
    .catch(fail);
}

// 제품 저장
async function isSaveMyPeripheral(data, success, fail) {
  await apiAuth
    .post(`/peripherals/${data[0]}/${data[1]}`)
    .then(success)
    .catch(fail);
}

export {
  isCallPeripheralRecommend,
  isCallPeripheralList,
  isCallPeripheralReview,
  isCreatePeripheralReview,
  isDeletePeripheralReview,
  isSaveMyPeripheral,
  isUpdatePeripheralReview,
};
