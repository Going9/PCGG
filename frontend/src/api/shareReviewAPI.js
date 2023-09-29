import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();


async function loadShareReviewAPI(data, success, fail) {
  const params = { pages : data.page }
  await apiAuth
  .get(`/shares/${data.articleId}/comments`, JSON.stringify(params))
  .then(success)
  .catch(fail);

}

async function createShareReviewAPI(data, success, fail) {
  const body = { content : data.content}
  await apiAuth
  .post(`/shares/${data.articleId}/comments`, JSON.stringify(body))
  .then(success)
  .catch(fail);
}

async function modifyShareReviewAPI(data, success, fail) {
  const body = { content : data.content}
  await apiAuth
  .put(`/shares/comments/${data.commentId}`, JSON.stringify(body))
  .then(success)
  .catch(fail);
}

async function deleteShareReviewAPI(data, success, fail) {
  await apiAuth
  .delete(`/shares/comments/${data.commentId}`)
  .then(success)
  .catch(fail);
}


export { loadShareReviewAPI, createShareReviewAPI, modifyShareReviewAPI, deleteShareReviewAPI}
