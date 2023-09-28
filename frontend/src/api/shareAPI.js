import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

async function loadShareListAPI(body, success, fail) {
  const params = { pages : body.page }
  await api.get("/shares/", JSON.stringify(params)).then(success).catch(fail);
}

async function createSharePostAPI( data, success, fail) {
  await apiAuth
  .post(`/shares/`, JSON.stringify(data))
  .then(success)
  .catch(fail);
}

async function likeSharePostAPI(data, success, fail) {
  const body = { mark : data.mark }
  await apiAuth
  .put(`/shares/${data.articleId}/marks`, JSON.stringify(body))
  .then(success)
  .catch(fail);

}

async function loadLikeHistoryAPI(data, success, fail) {
  await apiAuth
  .get(`/shares/${data.articleId}/marks`)
  .then(success)
  .catch(fail);

}

export { loadShareListAPI, createSharePostAPI, likeSharePostAPI, loadLikeHistoryAPI}
