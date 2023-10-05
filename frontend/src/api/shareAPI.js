import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

async function loadShareListAPI(data, success, fail) {
  const params = { pages: data.page, q: data.q };
  await api.get("/shares/", { params }).then(success).catch(fail);
}

async function loadShareDetailAPI(data, success, fail) {
  await api.get(`/shares/${data.articleId}`).then(success).catch(fail);
}

async function createSharePostAPI(data, success, fail) {
  await apiAuth
    .post(`/shares/`, JSON.stringify(data))
    .then(success)
    .catch(fail);
}

async function deleteSharePostAPI(data, success, fail) {
  await apiAuth.delete(`/shares/${data.articleId}`).then(success).catch(fail);
}

async function likeSharePostAPI(data, success, fail) {
  const body = { mark: data.mark };
  await apiAuth
    .put(`/shares/${data.articleId}/marks`, JSON.stringify(body))
    .then(success)
    .catch(fail);
}

async function loadLikeHistoryAPI(data, success, fail) {
  await apiAuth
    .get(`/shares/${data.articleId}/author-mark-info`)
    .then(success)
    .catch(fail);
}

async function saveMyQuoteAPI(data, success, fail) {
  await apiAuth
    .post(`/shares/${data.articleId}/quotes`, JSON.stringify(data))
    .then(success)
    .catch(fail);
}

export {
  loadShareListAPI,
  loadShareDetailAPI,
  createSharePostAPI,
  likeSharePostAPI,
  loadLikeHistoryAPI,
  deleteSharePostAPI,
  saveMyQuoteAPI,
};
