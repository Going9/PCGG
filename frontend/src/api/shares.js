import { apiSpringInstance, apiSpringAuthInstance } from "./index.js";

const api = apiSpringInstance();
const apiAuth = apiSpringAuthInstance();

async function loadSharedList(body, success, fail) {
  const params = { pages : body.page }
  await api.get("/shares/", JSON.stringify(params)).then(success).catch(fail);
}

async function createSharedPost(data, success, fail) {
const body = { data }
  await apiAuth.post("/shares/", JSON.stringify(body)).then(success).catch(fail);
}


export { loadSharedList, createSharedPost}
