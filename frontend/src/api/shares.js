import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

async function loadSharedList(body, success, fail) {
  const params = { pages : body.page }
  await api.get("/shares/", JSON.stringify(params)).then(success).catch(fail);
}

async function createSharedPost( data, success, fail) {
  await apiAuth
  .post(`/shares/`, JSON.stringify(data))
  .then(success)
  .catch(fail);
}


export { loadSharedList, createSharedPost}
